package com.spring.boot.resturantbackend.vm.product;

import com.spring.boot.resturantbackend.dto.product.ProductDetailsDto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductRequestVm {
    private Long id;
    @NotEmpty(message = "not_empty.name")
    @Size(min = 7, max = 50, message = "size.name")
    private String name;
    private String imagePath;
    @NotEmpty(message = "not_empty.description")
    private String description;
    @NotNull(message = "not_empty.price")
    @DecimalMin(value = "0.0", inclusive = false, message = "positive.price")
    private Double price;
    private Long categoryId;
    private ProductDetailsDto productDetails;
}
