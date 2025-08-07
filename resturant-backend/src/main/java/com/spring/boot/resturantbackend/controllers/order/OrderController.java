package com.spring.boot.resturantbackend.controllers.order;

import com.spring.boot.resturantbackend.dto.ExceptionDto;
import com.spring.boot.resturantbackend.dto.OrderDto;
import com.spring.boot.resturantbackend.services.order.OrderService;
import com.spring.boot.resturantbackend.vm.RequestOrderVm;
import com.spring.boot.resturantbackend.vm.RequestUpdateStatusOrder;
import com.spring.boot.resturantbackend.vm.ResponseOrderVm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Operation(
            summary = "Retrieve all accessible orders",
            description = "Get all orders that the authenticated (user/admin) has access to. " +
                          "Regular users/admin can only see their own orders, while admins can see all orders in the system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved orders",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OrderDto.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Access denied",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            )
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all-request-orders")
    public ResponseEntity<List<OrderDto>> getRequestOrders() {
        return ResponseEntity.ok(orderService.getAccessibleOrders());
    }

    @Operation(
            summary = "Create a new order",
            description = "Submit a new order request. The authenticated user will be set as the order owner. " +
                          "The order will be created with a pending status and can be processed by administrators.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Order successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseOrderVm.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request - Invalid order data",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Access denied",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Unprocessable entity - Validation failed",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            )
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/request-order")
    public ResponseEntity<ResponseOrderVm> requestOrder(@RequestBody @Valid RequestOrderVm requestOrderDto) {
        return ResponseEntity.created(URI.create("/request-order")).body(orderService.requestOrder(requestOrderDto));
    }

    @Operation(
            summary = "Update order status",
            description = "Update the status of an existing order. This operation is restricted to administrators only. " +
                          "Common status updates include: PENDING, CONFIRMED, IN_PREPARATION, READY, DELIVERED, CANCELLED.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Order status successfully updated"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request - Invalid update data",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Admin role required",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Unprocessable entity - Validation failed",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-order")
    public ResponseEntity<Void> updateOrder(@RequestBody @Valid RequestUpdateStatusOrder requestUpdateStatusOrder) {
        orderService.updateOrder(requestUpdateStatusOrder);
        return ResponseEntity.noContent().build();
    }

}
