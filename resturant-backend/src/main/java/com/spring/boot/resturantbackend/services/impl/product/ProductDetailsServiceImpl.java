package com.spring.boot.resturantbackend.services.impl.product;

import com.spring.boot.resturantbackend.dto.ProductDetailsDto;
import com.spring.boot.resturantbackend.mappers.ProductMapper;
import com.spring.boot.resturantbackend.models.product.ProductDetails;
import com.spring.boot.resturantbackend.repositories.product.ProductDetailsRepo;
import com.spring.boot.resturantbackend.services.product.ProductDetailsService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {
    @Autowired
    private ProductDetailsRepo productDetailsRepo;

    @Override
    public ProductDetailsDto addProductDetailsToProduct(ProductDetailsDto productDetailsDto) {
        try {
            if (Objects.nonNull(productDetailsDto.getId())) {
                throw new SystemException("id.must_be.null");
            }
            ProductDetails productDetails = ProductMapper.PRODUCT_MAPPER.toProductDetails(productDetailsDto);
            productDetails = productDetailsRepo.save(productDetails);
            return ProductMapper.PRODUCT_MAPPER.toProductDetailsDto(productDetails);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
