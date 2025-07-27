package com.spring.boot.resturantbackend.services.order;

import com.spring.boot.resturantbackend.dto.OrderDto;

import java.util.List;

public interface OrderAccessStrategy {
    List<OrderDto> getAccessibleOrders();
}
