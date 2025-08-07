package com.spring.boot.resturantbackend.controllers.order;

import com.spring.boot.resturantbackend.dto.ExceptionDto;
import com.spring.boot.resturantbackend.dto.OrderDto;
import com.spring.boot.resturantbackend.services.order.OrderService;
import com.spring.boot.resturantbackend.vm.RequestOrderVm;
import com.spring.boot.resturantbackend.vm.RequestUpdateStatusOrder;
import com.spring.boot.resturantbackend.vm.ResponseOrderVm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Order Controller", description = "operations on Order")
@RestController
@RequestMapping("/orders")
@CrossOrigin("http://localhost:4200")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Operation(summary = "get orders", description = "get orders for admin and user")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Http Status get orders"
            ),
            @ApiResponse(
                    responseCode = "500", description = "Http Status internal server error",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
    })
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
