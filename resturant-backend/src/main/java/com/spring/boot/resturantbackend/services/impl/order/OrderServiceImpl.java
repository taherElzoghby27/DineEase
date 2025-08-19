package com.spring.boot.resturantbackend.services.impl.order;

import com.spring.boot.resturantbackend.dto.OrderDto;
import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.exceptions.BadRequestException;
import com.spring.boot.resturantbackend.exceptions.NotFoundResourceException;
import com.spring.boot.resturantbackend.mappers.OrderMapper;
import com.spring.boot.resturantbackend.models.Order;
import com.spring.boot.resturantbackend.models.product.Product;
import com.spring.boot.resturantbackend.models.security.Account;
import com.spring.boot.resturantbackend.repositories.OrderRepo;
import com.spring.boot.resturantbackend.services.CategoryService;
import com.spring.boot.resturantbackend.services.order.OrderService;
import com.spring.boot.resturantbackend.services.product.ProductService;
import com.spring.boot.resturantbackend.services.security.AccountService;
import com.spring.boot.resturantbackend.utils.enums.OrderStatus;
import com.spring.boot.resturantbackend.utils.enums.RoleEnum;
import com.spring.boot.resturantbackend.utils.SecurityUtils;
import com.spring.boot.resturantbackend.vm.RequestOrderVm;
import com.spring.boot.resturantbackend.vm.RequestUpdateStatusOrder;
import com.spring.boot.resturantbackend.vm.ResponseOrderVm;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final ProductService productService;
    private final AccountService accountService;
    private final OrderStrategyFactory orderStrategyFactory;
    private final CategoryService categoryService;


    @CachePut(value = "orders", key = "#result.id")
    @CacheEvict(value = "orders", allEntries = true)
    @Override
    @Transactional
    public ResponseOrderVm requestOrder(RequestOrderVm requestOrderVm) {
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        if (Objects.nonNull(requestOrderVm.getId())) {
            throw new BadRequestException("id.must_be.null");
        }
        if (Objects.isNull(requestOrderVm.getProductsIds()) || requestOrderVm.getProductsIds().isEmpty()) {
            throw new BadRequestException("products_id.must_be.not_null");
        }
        //get products
        List<Product> products = productService.listOfIdsToListOfProducts(requestOrderVm.getProductsIds());
        //get account
        Account account = accountService.idToAccount(accountDto.getId());
        //convert RequestOrderDto to order
        Order order = OrderMapper.ORDER_MAPPER.toOrder(requestOrderVm);
        //set products to order
        order.setAccount(account);
        //set products to order
        order.setProducts(products);
        //set status for order
        order.setStatus(OrderStatus.Pending.toString());
        //create code
        String orderCode = generateCodeForOrder();
        order.setCode(orderCode);
        //save order
        order = orderRepo.save(order);
        categoryService.updateRecommendedCategory();
        return OrderMapper.ORDER_MAPPER.toResponseOrderVm(order);
    }

    //generate code for order
    private static String generateCodeForOrder() {
        return UUID.randomUUID().toString().substring(0, 8);
    }


    @Override
    public List<OrderDto> getAccessibleOrders() {
        boolean isAdmin = SecurityUtils.hasRole(RoleEnum.ADMIN);
        RoleEnum role = isAdmin ? RoleEnum.ADMIN : RoleEnum.USER;
        return orderStrategyFactory.getStrategy(role.toString()).getAccessibleOrders();
    }

    @CacheEvict(value = "orders", allEntries = true)
    @Override
    @Transactional
    public void updateOrder(RequestUpdateStatusOrder requestUpdateStatusOrder) {
        Order order = getOrderById(requestUpdateStatusOrder.getId());
        if (Objects.isNull(order)) {
            throw new NotFoundResourceException("order.not.found");
        }
        OrderStatus.valueOf(requestUpdateStatusOrder.getStatus());
        order.setStatus(requestUpdateStatusOrder.getStatus());
        orderRepo.save(order);
    }


    private Order getOrderById(Long id) {
        return orderRepo.findById(id).orElse(null);
    }
}
