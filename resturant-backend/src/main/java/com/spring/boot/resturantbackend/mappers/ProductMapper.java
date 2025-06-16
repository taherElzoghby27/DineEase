package com.spring.boot.resturantbackend.mappers;

import com.spring.boot.resturantbackend.dto.product.ProductDetailsDto;
import com.spring.boot.resturantbackend.dto.product.ProductDto;
import com.spring.boot.resturantbackend.models.product.Product;
import com.spring.boot.resturantbackend.models.product.ProductDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

    ProductDto toProductDto(Product product);

    Product toProduct(ProductDto productDto);

    @Mapping(source = "product", target = "product_id", ignore = true)
    ProductDetailsDto toProductDetailsDto(ProductDetails productDetails);

    @Mapping(source = "product_id", target = "product", ignore = true)
    ProductDetails toProductDetails(ProductDetailsDto productDetailsDto);
}
