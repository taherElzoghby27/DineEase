package com.spring.boot.backend.controllers;

import com.spring.boot.backend.dto.CategoryDto;
import com.spring.boot.backend.dto.ProductDto;
import com.spring.boot.backend.services.ProductService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all-products-by-category-id")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategoryId(@RequestParam Long categoryId) throws SystemException {
        return ResponseEntity.ok(productService.getAllProductsByCategoryId(categoryId));
    }
}
