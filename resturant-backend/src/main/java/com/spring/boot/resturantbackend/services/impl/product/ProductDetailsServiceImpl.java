package com.spring.boot.resturantbackend.services.impl.product;

import com.spring.boot.resturantbackend.dto.product.ProductDetailsDto;
import com.spring.boot.resturantbackend.dto.product.ProductDto;
import com.spring.boot.resturantbackend.mappers.ProductMapper;
import com.spring.boot.resturantbackend.models.product.ProductDetails;
import com.spring.boot.resturantbackend.repositories.product.ProductDetailsRepo;
import com.spring.boot.resturantbackend.services.product.ProductDetailsService;
import com.spring.boot.resturantbackend.services.product.ProductService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {
    @Autowired
    private ProductDetailsRepo productDetailsRepo;
    @Autowired
    private ProductService productService;

    @Override
    @CacheEvict(value = "products", allEntries = true)
    @CachePut(value = "products", key = "#result.productId")
    public ProductDetailsDto addProductDetailsToProduct(ProductDetailsDto productDetailsDto) {
        try {
            if (Objects.nonNull(productDetailsDto.getId())) {
                throw new SystemException("id.must_be.null");
            }
            ProductDetails productDetails = ProductMapper.PRODUCT_MAPPER.toProductDetails(productDetailsDto);
            //set product to product details
            ProductDto productById = productService.getProductById(productDetailsDto.getProductId());
            productDetails.setProduct(ProductMapper.PRODUCT_MAPPER.toProduct(productById));
            productDetails = productDetailsRepo.save(productDetails);
            return ProductMapper.PRODUCT_MAPPER.toProductDetailsDto(productDetails);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductDetailsDto getProductDetailsByProductId(Long id) {
        try {
            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }
            Optional<ProductDetails> result = productDetailsRepo.findByProductId(id);
            if (result.isEmpty()) {
                throw new SystemException("product_details.not.found");
            }
            ProductDetails productDetails = result.get();
            ProductDetailsDto productDetailsDto = ProductMapper.PRODUCT_MAPPER.toProductDetailsDto(result.get());
            productDetailsDto.setProductId(productDetails.getProduct().getId());
            return productDetailsDto;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    @CachePut(value = "products", key = "#result.productId")
    public ProductDetailsDto updateProductDetails(ProductDetailsDto productDetailsDto) {
        try {
            if (Objects.isNull(productDetailsDto.getId())) {
                throw new SystemException("id.must_be.not_null");
            }
            ProductDetailsDto existingProductDetails = getProductDetailsByProductId(productDetailsDto.getProductId());
            existingProductDetails.setPreparationTime(productDetailsDto.getPreparationTime());
            existingProductDetails.setProductCode(productDetailsDto.getProductCode());
            ProductDetails productDetails = ProductMapper.PRODUCT_MAPPER.toProductDetails(existingProductDetails);
            //set product to product details
            productDetails.setProduct(ProductMapper.PRODUCT_MAPPER.toProduct(productService.getProductById(productDetailsDto.getProductId())));
            productDetails = productDetailsRepo.save(productDetails);
            return ProductMapper.PRODUCT_MAPPER.toProductDetailsDto(productDetails);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
