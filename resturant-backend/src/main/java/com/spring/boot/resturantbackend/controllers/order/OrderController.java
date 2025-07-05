package com.spring.boot.resturantbackend.controllers.order;

import com.spring.boot.resturantbackend.dto.OrderDto;
import com.spring.boot.resturantbackend.services.order.OrderService;
import com.spring.boot.resturantbackend.vm.RequestOrderVm;
import com.spring.boot.resturantbackend.vm.RequestUpdateStatusOrder;
import com.spring.boot.resturantbackend.vm.ResponseOrderVm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin("http://localhost:4200")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all-request-orders")
    public ResponseEntity<List<OrderDto>> getRequestOrders() {
        return ResponseEntity.ok(orderService.getAccessibleOrders());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/request-order")
    public ResponseEntity<ResponseOrderVm> requestOrder(@RequestBody @Valid RequestOrderVm requestOrderDto) {
        return ResponseEntity.created(URI.create("/request-order")).body(orderService.requestOrder(requestOrderDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-order")
    public ResponseEntity<Void> updateOrder(@RequestBody @Valid RequestUpdateStatusOrder requestUpdateStatusOrder) {
        orderService.updateOrder(requestUpdateStatusOrder);
        return ResponseEntity.noContent().build();
    }

}
