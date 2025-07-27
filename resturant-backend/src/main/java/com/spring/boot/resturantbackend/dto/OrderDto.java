package com.spring.boot.resturantbackend.dto;

import com.spring.boot.resturantbackend.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderDto implements Serializable {
    private Long id;
    private Long accountId;
    private double totalPrice;
    private Long totalNumber;
    private String status;
    private String code;
    private List<ProductDto> products;
    private String createdBy;
}
