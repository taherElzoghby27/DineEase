package com.spring.boot.resturantbackend.services.order;

import com.spring.boot.resturantbackend.dto.OrderDto;
import com.spring.boot.resturantbackend.vm.RequestOrderVm;
import com.spring.boot.resturantbackend.vm.ResponseOrderVm;

import java.util.List;

public interface OrderService {
    ResponseOrderVm requestOrder(RequestOrderVm requestOrderDto);

    List<OrderDto> getAccessibleOrders();
}
