package com.spring.boot.resturantbackend.services.impl.order.impl_strategy;

import com.spring.boot.resturantbackend.dto.OrderDto;
import com.spring.boot.resturantbackend.exceptions.NotFoundResourceException;
import com.spring.boot.resturantbackend.mappers.OrderMapper;
import com.spring.boot.resturantbackend.models.Order;
import com.spring.boot.resturantbackend.repositories.OrderRepo;
import com.spring.boot.resturantbackend.services.order.OrderAccessStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminOrders implements OrderAccessStrategy {
    @Autowired
    private OrderRepo orderRepo;

    @Cacheable(value = "orders", key = "'all'")
    @Override
    public List<OrderDto> getAccessibleOrders() {
        List<Order> orders = orderRepo.findAllOrderByUpdatedDateDesc();
        if (orders.isEmpty()) {
            throw new NotFoundResourceException("error.orders.is.empty");
        }
        return orders.stream().map(OrderMapper.ORDER_MAPPER::toOrderDto).toList();
    }
}
