package com.spring.boot.resturantbackend.services.impl.order.impl_strategy;

import com.spring.boot.resturantbackend.dto.OrderDto;
import com.spring.boot.resturantbackend.mappers.OrderMapper;
import com.spring.boot.resturantbackend.models.Order;
import com.spring.boot.resturantbackend.repositories.OrderRepo;
import com.spring.boot.resturantbackend.services.order.OrderAccessStrategy;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminOrders implements OrderAccessStrategy {
    @Autowired
    private OrderRepo orderRepo;

    @Override
    public List<OrderDto> getAccessibleOrders() {
        try {
            List<Order> orders = orderRepo.findAll();
            if (orders.isEmpty()) {
                throw new SystemException("error.orders.is.empty");
            }
            return orders.stream().map(OrderMapper.ORDER_MAPPER::toOrderDto).toList();
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
