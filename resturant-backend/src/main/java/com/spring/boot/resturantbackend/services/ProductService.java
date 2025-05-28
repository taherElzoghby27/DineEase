package com.spring.boot.resturantbackend.services;

import com.spring.boot.resturantbackend.dto.ProductDto;
import jakarta.transaction.SystemException;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts(int page, int size) throws SystemException;

    List<ProductDto> getAllProductsByCategoryId(Long id, int page, int size) throws SystemException;

    ProductDto createProduct(ProductDto productDto) throws SystemException;

    List<ProductDto> createListOfProduct(List<ProductDto> productDto) throws SystemException;

    ProductDto updateProduct(ProductDto productDto) throws SystemException;

    List<ProductDto> updateListOfProduct(List<ProductDto> productDto) throws SystemException;

    void deleteProductById(Long id) throws SystemException;

    void deleteListOfProduct(List<Long> productIds) throws SystemException;

    ProductDto getProductById(Long id) throws SystemException;

    List<ProductDto> getAllProductsByKey(String key, int page, int size) throws SystemException;

    List<ProductDto> getAllProductsByCategoryIdAndKey(Long categoryId, String key, int page, int size) throws SystemException;
}
