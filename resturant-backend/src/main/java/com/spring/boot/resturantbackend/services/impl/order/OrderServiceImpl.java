package com.spring.boot.resturantbackend.services.impl.order;

import com.spring.boot.resturantbackend.dto.OrderDto;
import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.mappers.OrderMapper;
import com.spring.boot.resturantbackend.models.Order;
import com.spring.boot.resturantbackend.models.product.Product;
import com.spring.boot.resturantbackend.models.security.Account;
import com.spring.boot.resturantbackend.repositories.OrderRepo;
import com.spring.boot.resturantbackend.services.order.OrderService;
import com.spring.boot.resturantbackend.services.product.ProductService;
import com.spring.boot.resturantbackend.services.security.AccountService;
import com.spring.boot.resturantbackend.utils.enums.OrderStatus;
import com.spring.boot.resturantbackend.utils.enums.RoleEnum;
import com.spring.boot.resturantbackend.utils.SecurityUtils;
import com.spring.boot.resturantbackend.vm.RequestOrderVm;
import com.spring.boot.resturantbackend.vm.RequestUpdateStatusOrder;
import com.spring.boot.resturantbackend.vm.ResponseOrderVm;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ProductService productService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderStrategyFactory orderStrategyFactory;


    @CachePut(value = "orders", key = "#result.id")
    @CacheEvict(value = "orders", allEntries = true)
    @Override
    public ResponseOrderVm requestOrder(RequestOrderVm requestOrderVm) {
        try {
            if (Objects.nonNull(requestOrderVm.getId())) {
                throw new SystemException("id.must_be.null");
            }
            //get products
            List<Product> products = productService.listOfIdsToListOfProducts(requestOrderVm.getProductsIds());
            //get account
            Account account = accountService.idToAccount(requestOrderVm.getAccountId());
            //convert RequestOrderDto to order
            Order order = OrderMapper.ORDER_MAPPER.toOrder(requestOrderVm);
            //set products to order
            order.setAccount(account);
            //set products to order
            order.setProducts(products);
            //set status for order
            order.setStatus(OrderStatus.Pending.toString());
            //save order
            order = orderRepo.save(order);
            return OrderMapper.ORDER_MAPPER.toResponseOrderVm(order);
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public List<OrderDto> getAccessibleOrders() {
        boolean isAdmin = SecurityUtils.hasRole(RoleEnum.ADMIN);
        RoleEnum role = isAdmin ? RoleEnum.ADMIN : RoleEnum.USER;
        return orderStrategyFactory.getStrategy(role.toString()).getAccessibleOrders();
    }

    @CacheEvict(value = "orders", key = "'all'")
    @CachePut(value = "orders", key = "#requestUpdateStatusOrder.id")
    @Override
    public void updateOrder(RequestUpdateStatusOrder requestUpdateStatusOrder) {
        try {
            Order order = getOrderById(requestUpdateStatusOrder.getId());
            if (Objects.isNull(order)) {
                throw new SystemException("order.not.found");
            }
            OrderStatus.valueOf(requestUpdateStatusOrder.getStatus());
            order.setStatus(requestUpdateStatusOrder.getStatus());
            orderRepo.save(order);
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    private Order getOrderById(Long id) {
        return orderRepo.findById(id).orElse(null);
    }
}
