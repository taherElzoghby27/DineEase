package com.spring.boot.backend.services;

import com.spring.boot.backend.dto.ProductDto;
import jakarta.transaction.SystemException;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProductsByCategoryId(Long id) throws SystemException;

    ProductDto createProduct(ProductDto productDto) throws SystemException;

    List<ProductDto> createListOfProduct(List<ProductDto> productDto) throws SystemException;

    ProductDto updateProduct(ProductDto productDto) throws SystemException;

    List<ProductDto> updateListOfProduct(List<ProductDto> productDto) throws SystemException;

    void deleteProductById(Long id) throws SystemException;

    void deleteListOfProduct(List<Long> productIds) throws SystemException;

    ProductDto getProductById(Long id) throws SystemException;

    List<ProductDto> getAllProductsByKey(String key) throws SystemException;
}
