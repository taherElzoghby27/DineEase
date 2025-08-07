package com.spring.boot.resturantbackend.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "ProductDetailsDto",
        description = "Data transfer object containing detailed supplementary information for restaurant products including preparation details and internal tracking codes"
)
public class ProductDetailsDto implements Serializable {
    @Schema(
            description = "Unique identifier for the product details record (auto-generated for new records)",
            example = "456",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Long id;

    @Schema(
            description = "Estimated time required to prepare this product from order to completion. " +
                          "Can include cooking, assembly, and plating time",
            example = "15-20 minutes",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "error.empty.preparationTime")
    private String preparationTime;

    @Schema(
            description = "Internal product code used for inventory management, kitchen operations, or POS system integration. " +
                          "Must be unique across all products",
            example = "PIZZA-MARG-001",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "error.empty.productCode")
    private String productCode;

    @Schema(
            description = "Reference to the main product this detailed information belongs to. " +
                          "Links to the primary product record in the system",
            example = "123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long productId;
}
