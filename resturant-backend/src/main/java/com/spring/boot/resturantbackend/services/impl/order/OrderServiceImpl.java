package com.spring.boot.resturantbackend.services.impl.order;

import com.spring.boot.resturantbackend.dto.OrderDto;
import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.dto.security.RoleDto;
import com.spring.boot.resturantbackend.mappers.OrderMapper;
import com.spring.boot.resturantbackend.models.Order;
import com.spring.boot.resturantbackend.models.product.Product;
import com.spring.boot.resturantbackend.models.security.Account;
import com.spring.boot.resturantbackend.repositories.OrderRepo;
import com.spring.boot.resturantbackend.services.order.OrderService;
import com.spring.boot.resturantbackend.services.product.ProductService;
import com.spring.boot.resturantbackend.services.security.AccountService;
import com.spring.boot.resturantbackend.services.security.RoleService;
import com.spring.boot.resturantbackend.utils.OrderStatus;
import com.spring.boot.resturantbackend.utils.RoleEnum;
import com.spring.boot.resturantbackend.utils.SecurityUtils;
import com.spring.boot.resturantbackend.vm.RequestOrderVm;
import com.spring.boot.resturantbackend.vm.ResponseOrderVm;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RoleService roleService;


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
        try {
            //get account id
            AccountDto accountDto = SecurityUtils.getCurrentAccount();
            List<RoleDto> roles = roleService.getRoleByAccountId(accountDto.getId());
            if (roles.isEmpty()) {
                throw new SystemException("role.not.fount");
            }
            // Fetch orders based on role
            List<Order> orders = isAdmin(roles)
                    ? orderRepo.findAll()
                    : orderRepo.findByAccountId(accountDto.getId());
            if (orders.isEmpty()) {
                throw new SystemException("error.orders.is.empty");
            }
            return orders.stream().map(OrderMapper.ORDER_MAPPER::toOrderDto).toList();
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // Helper method to check for ADMIN role
    private boolean isAdmin(List<RoleDto> roles) {
        return roles.stream().anyMatch(role -> role.getRole().equals(RoleEnum.ADMIN.toString()));
    }
}
