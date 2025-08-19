package com.spring.boot.resturantbackend.services.impl.product;

import com.spring.boot.resturantbackend.dto.product.ProductDetailsDto;
import com.spring.boot.resturantbackend.dto.product.ProductDto;
import com.spring.boot.resturantbackend.exceptions.BadRequestException;
import com.spring.boot.resturantbackend.exceptions.NotFoundResourceException;
import com.spring.boot.resturantbackend.mappers.ProductMapper;
import com.spring.boot.resturantbackend.models.product.Product;
import com.spring.boot.resturantbackend.models.product.ProductDetails;
import com.spring.boot.resturantbackend.repositories.product.ProductDetailsRepo;
import com.spring.boot.resturantbackend.services.product.ProductDetailsService;
import com.spring.boot.resturantbackend.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailsService {
    private final ProductDetailsRepo productDetailsRepo;
    private final ProductService productService;

    @Override
    @CacheEvict(value = "products", allEntries = true)
    @CachePut(value = "products", key = "#result.productId")
    @Transactional
    public ProductDetailsDto addProductDetailsToProduct(ProductDetailsDto productDetailsDto) {
        if (Objects.nonNull(productDetailsDto.getId())) {
            throw new BadRequestException("id.must_be.null");
        }
        if (Objects.isNull(productDetailsDto.getProductId())) {
            throw new BadRequestException("product_id.must_be.not_empty");
        }
        ProductDetails productDetails = ProductMapper.PRODUCT_MAPPER.toProductDetails(productDetailsDto);
        //set product to product details
        ProductDto productById = productService.getProductById(productDetailsDto.getProductId());
        productDetails.setProduct(ProductMapper.PRODUCT_MAPPER.toProduct(productById));
        productDetails = productDetailsRepo.save(productDetails);
        return ProductMapper.PRODUCT_MAPPER.toProductDetailsDto(productDetails);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ProductDetailsDto getProductDetailsByProductId(Long id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("id.must_be.not_null");
        }
        Optional<ProductDetails> result = productDetailsRepo.findByProductId(id);
        if (result.isEmpty()) {
            throw new NotFoundResourceException("product_details.not.found");
        }
        ProductDetails productDetails = result.get();
        ProductDetailsDto productDetailsDto = ProductMapper.PRODUCT_MAPPER.toProductDetailsDto(result.get());
        productDetailsDto.setProductId(productDetails.getProduct().getId());
        return productDetailsDto;
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    @CachePut(value = "products", key = "#result.productId")
    @Transactional
    public ProductDetailsDto updateProductDetails(ProductDetailsDto productDetailsDto) {
        if (Objects.isNull(productDetailsDto.getId())) {
            throw new BadRequestException("id.must_be.not_null");
        }
        if (Objects.isNull(productDetailsDto.getProductId())) {
            throw new BadRequestException("product_id.must_be.not_empty");
        }
        ProductDetailsDto existingProductDetails = getProductDetailsByProductId(productDetailsDto.getProductId());
        existingProductDetails.setPreparationTime(productDetailsDto.getPreparationTime());
        existingProductDetails.setProductCode(productDetailsDto.getProductCode());
        ProductDetails productDetails = ProductMapper.PRODUCT_MAPPER.toProductDetails(existingProductDetails);
        //set product to product details
        ProductDto productById = productService.getProductById(productDetailsDto.getProductId());
        Product product = ProductMapper.PRODUCT_MAPPER.toProduct(productById);
        productDetails.setProduct(product);
        productDetails = productDetailsRepo.save(productDetails);
        return ProductMapper.PRODUCT_MAPPER.toProductDetailsDto(productDetails);
    }
}
