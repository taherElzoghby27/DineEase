package com.spring.boot.resturantbackend.services.product;

import com.spring.boot.resturantbackend.dto.product.ProductDetailsDto;

public interface ProductDetailsService {
    ProductDetailsDto addProductDetailsToProduct(ProductDetailsDto productDetails);

    ProductDetailsDto getProductDetailsByProductId(Long id);

    ProductDetailsDto updateProductDetails(ProductDetailsDto productDetails);

    void deleteProductDetailsByProductId(Long id);
}
