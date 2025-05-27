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
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam int page, @RequestParam int size)
            throws SystemException {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @GetMapping("/all-products/{id}")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategoryId(@PathVariable Long id, @RequestParam int page, @RequestParam int size)
            throws SystemException {
        return ResponseEntity.ok(productService.getAllProductsByCategoryId(id, page, size));
    }

    @GetMapping("/all-products-by-key")
    public ResponseEntity<List<ProductDto>> getAllProductsByKey(@RequestParam String key, @RequestParam int page, @RequestParam int size)
            throws SystemException {
        return ResponseEntity.ok(productService.getAllProductsByKey(key, page, size));
    }
}
