package com.spring.boot.resturantbackend.controllers;
import com.spring.boot.resturantbackend.dto.ProductDto;
import com.spring.boot.resturantbackend.services.ProductService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("http://localhost:4200")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all-products")
    public ResponseEntity<List<ProductDto>> getAllProducts()  {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("/all-products-by-category-id")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategoryId(@RequestParam Long categoryId) throws SystemException {
        return ResponseEntity.ok(productService.getAllProductsByCategoryId(categoryId));
    }
}
