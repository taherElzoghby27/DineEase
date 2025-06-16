package com.spring.boot.resturantbackend.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String preparationTime;
    private String productCode;
    private Long product_id;
}
