package com.spring.boot.resturantbackend.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailsDto {
    private Long id;
    @NotEmpty(message = "error.empty.preparationTime")
    private String preparationTime;
    @NotEmpty(message = "error.empty.productCode")
    private String productCode;
    private Long product_id;
}
