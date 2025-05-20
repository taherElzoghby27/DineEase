package com.spring.boot.backend.services.impl;

import com.spring.boot.backend.dto.ProductDto;
import com.spring.boot.backend.mappers.ProductMapper;
import com.spring.boot.backend.models.Product;
import com.spring.boot.backend.repositories.ProductRepo;
import com.spring.boot.backend.services.ProductService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<ProductDto> getAllProductsByCategoryId(Long id) throws SystemException {
        if (Objects.isNull(id)) {
            throw new SystemException("id.must_be.not_null");
        }
        List<Product> products = productRepo.findAllProductsByCategoryId(id);
        return products.stream().map(ProductMapper.INSTANCE_PRODUCT::toProductDto).toList();
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) throws SystemException {
        try {
            if (Objects.nonNull(productDto.getId())) {
                throw new SystemException("id.must_be.null");
            }
            Product product = ProductMapper.INSTANCE_PRODUCT.toProduct(productDto);
            product = productRepo.save(product);
            return ProductMapper.INSTANCE_PRODUCT.toProductDto(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SystemException("something.error");
        }
    }

    @Override
    public List<ProductDto> createListOfProduct(List<ProductDto> productDto) throws SystemException {
        try {
            if (productDto.isEmpty()) {
                throw new SystemException("error.empty.list.product");
            }
            List<Product> products = productDto.stream().map(ProductMapper.INSTANCE_PRODUCT::toProduct).toList();
            products = productRepo.saveAll(products);
            return products.stream().map(ProductMapper.INSTANCE_PRODUCT::toProductDto).toList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SystemException("something.error");
        }
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) throws SystemException {
        try {
            if (Objects.isNull(productDto.getId())) {
                throw new SystemException("id.must_be.not_null");
            }
            Product product = ProductMapper.INSTANCE_PRODUCT.toProduct(productDto);
            product = productRepo.save(product);
            return ProductMapper.INSTANCE_PRODUCT.toProductDto(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SystemException("something.error");
        }
    }

    @Override
    public List<ProductDto> updateListOfProduct(List<ProductDto> productDto) throws SystemException {
        try {
            if (productDto.isEmpty()) {
                throw new SystemException("error.empty.list.category");
            }
            List<Product> products = productDto.stream().map(ProductMapper.INSTANCE_PRODUCT::toProduct).toList();
            products = productRepo.saveAll(products);
            return products.stream().map(ProductMapper.INSTANCE_PRODUCT::toProductDto).toList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SystemException("something.error");
        }
    }

    @Override
    public void deleteProductById(Long id) throws SystemException {
        try {
            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }
            getProductById(id);
            productRepo.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SystemException("something.error");
        }

    }

    @Override
    public void deleteListOfProduct(List<Long> productIds) throws SystemException {
        try {
            if (productIds.isEmpty()) {
                throw new SystemException("error.empty.list.category");
            }
            productRepo.deleteAllById(productIds);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SystemException("something.error");
        }
    }

    @Override
    public ProductDto getProductById(Long id) throws SystemException {
        try {
            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }
            Optional<Product> result = productRepo.findById(id);
            if (result.isEmpty()) {
                throw new SystemException("product.not.found");
            }
            return ProductMapper.INSTANCE_PRODUCT.toProductDto(result.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SystemException("something.error");
        }
    }

    @Override
    public List<ProductDto> getAllProductsByKey(String key) throws SystemException {
        try {
            if (Objects.isNull(key)) {
                throw new SystemException("key.null");
            }
            List<Product> result = productRepo.getAllProductsByKey(key);
            if (result.isEmpty()) {
                throw new SystemException("product.not.found");
            }
            return result.stream().map(ProductMapper.INSTANCE_PRODUCT::toProductDto).toList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SystemException("something.error");
        }
    }
}
