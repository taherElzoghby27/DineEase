package com.spring.boot.resturantbackend.services.impl.order.impl_strategy;

import com.spring.boot.resturantbackend.dto.OrderDto;
import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.exceptions.NotFoundResourceException;
import com.spring.boot.resturantbackend.mappers.OrderMapper;
import com.spring.boot.resturantbackend.models.Order;
import com.spring.boot.resturantbackend.repositories.OrderRepo;
import com.spring.boot.resturantbackend.services.order.OrderAccessStrategy;
import com.spring.boot.resturantbackend.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserOrders implements OrderAccessStrategy {
    private final OrderRepo orderRepo;

    @Cacheable(value = "orders", keyGenerator = "accountIdGenerator")
    @Override
    public List<OrderDto> getAccessibleOrders() {
        //get account id
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        // Fetch orders
        List<Order> orders = orderRepo.findByAccountIdOrderByUpdatedDateDesc(accountDto.getId());
        if (orders.isEmpty()) {
            throw new NotFoundResourceException("error.orders.is.empty");
        }
        return orders.stream().map(OrderMapper.ORDER_MAPPER::toOrderDto).toList();
    }
}
