package com.spring.boot.resturantbackend.services.impl.product;

import com.spring.boot.resturantbackend.dto.product.ProductDetailsDto;
import com.spring.boot.resturantbackend.mappers.ProductMapper;
import com.spring.boot.resturantbackend.models.product.Product;
import com.spring.boot.resturantbackend.models.product.ProductDetails;
import com.spring.boot.resturantbackend.repositories.product.ProductDetailsRepo;
import com.spring.boot.resturantbackend.services.product.ProductDetailsService;
import com.spring.boot.resturantbackend.services.product.ProductService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ProductDetailsDto addProductDetailsToProduct(ProductDetailsDto productDetailsDto) {
        try {
            if (Objects.nonNull(productDetailsDto.getId())) {
                throw new SystemException("id.must_be.null");
            }
            ProductDetails productDetails = ProductMapper.PRODUCT_MAPPER.toProductDetails(productDetailsDto);
            //set product to product details
            productDetails.setProduct(
                    ProductMapper.PRODUCT_MAPPER.toProduct(
                            productService.getProductById(productDetailsDto.getProduct_id())
                    )
            );
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
            Optional<ProductDetails> result = productDetailsRepo.findById(id);
            if (result.isEmpty()) {
                throw new SystemException("product_details.not.found");
            }
            ProductDetails productDetails = result.get();
            ProductDetailsDto productDetailsDto = ProductMapper.PRODUCT_MAPPER.toProductDetailsDto(result.get());
            productDetailsDto.setProduct_id(productDetails.getProduct().getId());
            return productDetailsDto;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductDetailsDto updateProductDetails(ProductDetailsDto productDetailsDto) {
        try {
            if (Objects.isNull(productDetailsDto.getId())) {
                throw new SystemException("id.must_be.not_null");
            }
            ProductDetails productDetails = ProductMapper.PRODUCT_MAPPER.toProductDetails(productDetailsDto);
            //set product to product details
            productDetails.setProduct(
                    ProductMapper.PRODUCT_MAPPER.toProduct(
                            productService.getProductById(productDetailsDto.getProduct_id())
                    )
            );
            productDetails = productDetailsRepo.save(productDetails);
            return ProductMapper.PRODUCT_MAPPER.toProductDetailsDto(productDetails);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
