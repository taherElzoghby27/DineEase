package com.spring.boot.resturantbackend.dto;

import com.spring.boot.resturantbackend.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderDto {
    private Long id;
    private Long accountId;
    private double totalPrice;
    private Long totalNumber;
    List<ProductDto> products;
}
