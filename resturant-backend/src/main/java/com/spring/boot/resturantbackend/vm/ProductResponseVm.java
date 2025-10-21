package com.spring.boot.resturantbackend.vm;

import com.spring.boot.resturantbackend.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseVm implements Serializable {
    private List<ProductDto> products;
    private Long totalProducts;
}
