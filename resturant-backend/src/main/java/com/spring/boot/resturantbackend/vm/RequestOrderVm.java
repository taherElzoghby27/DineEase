package com.spring.boot.resturantbackend.vm;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(
        name = "RequestOrderVm",
        description = "Request model for creating a new restaurant order with product details and pricing information"
)
public class RequestOrderVm {
    private Long id;
    @NotNull(message = "error.account_id.not_empty")
    @Schema(
            description = "Unique identifier of the customer account placing the order",
            example = "67890",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long accountId;
    @NotNull(message = "error.total_price.not_empty")
    @Schema(
            description = "Total price of the order including all products and applicable taxes",
            example = "29.99",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minimum = "0.01"
    )
    private double totalPrice;
    @NotNull(message = "error.total_number.not_empty")
    @Schema(
            description = "Total number of items in the order (sum of quantities for all products)",
            example = "3",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minimum = "1"
    )
    private Long totalNumber;
    @NotEmpty(message = "error.products_ids.not_empty")
    @Schema(
            description = "List of product IDs included in the order. Each ID represents a menu item being ordered",
            example = "[101, 102, 103]",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 1
    )
    List<Long> productsIds;
}
