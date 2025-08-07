package com.spring.boot.resturantbackend.vm.product;

import com.spring.boot.resturantbackend.dto.product.ProductDetailsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(
        name = "ProductRequestVm",
        description = "Request model for creating or updating restaurant menu products with complete product information"
)
public class ProductRequestVm implements Serializable {
    private Long id;
    @NotEmpty(message = "not_empty.name")
    @Size(min = 7, max = 50, message = "size.name")
    @Schema(
            description = "Name of the product as it will appear on the menu. Must be between 7 and 50 characters",
            example = "Margherita Pizza",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 7,
            maxLength = 50
    )
    private String name;
    @Schema(
            description = "Path or URL to the product image for display purposes",
            example = "/images/products/margherita-pizza.jpg",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String imagePath;
    @NotEmpty(message = "not_empty.description")
    @Schema(
            description = "Detailed description of the product including ingredients, preparation method, or special features",
            example = "Traditional Italian pizza with fresh mozzarella, tomato sauce, and basil leaves on a thin crust",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String description;
    @NotNull(message = "not_empty.price")
    @DecimalMin(value = "0.0", inclusive = false, message = "positive.price")
    @Schema(
            description = "Price of the product in the restaurant's currency. Must be greater than 0",
            example = "15.99",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minimum = "0.01"
    )
    private Double price;
    @Schema(
            description = "Unique identifier of the category this product belongs to (e.g., appetizers, main courses, desserts)",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long categoryId;
    @Schema(
            description = "Additional detailed information about the product such as nutritional data, allergens, or preparation time",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private ProductDetailsDto productDetails;
}
