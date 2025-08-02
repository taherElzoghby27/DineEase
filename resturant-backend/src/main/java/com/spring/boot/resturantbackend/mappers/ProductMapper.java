package com.spring.boot.resturantbackend.mappers;

import com.spring.boot.resturantbackend.dto.product.ProductDetailsDto;
import com.spring.boot.resturantbackend.dto.product.ProductDto;
import com.spring.boot.resturantbackend.models.Category;
import com.spring.boot.resturantbackend.models.product.Product;
import com.spring.boot.resturantbackend.models.product.ProductDetails;
import com.spring.boot.resturantbackend.vm.product.ProductRequestVm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

    ProductDto toProductDto(Product product);

    Product toProduct(ProductDto productDto);

    @Mapping(source = "categoryId", target = "category", ignore = true)
    Product toProduct(ProductRequestVm productRequestVm);

    @Mapping(source = "category", target = "categoryId", qualifiedByName = "categoryToId")
    ProductRequestVm toProductRequestVm(Product product);

    @Mapping(source = "product", target = "productId", qualifiedByName = "productToId")
    ProductDetailsDto toProductDetailsDto(ProductDetails productDetails);

    @Mapping(source = "productId", target = "product", ignore = true)
    ProductDetails toProductDetails(ProductDetailsDto productDetailsDto);

    @Named("categoryToId")
    default Long categoryToId(Category category) {
        return category.getId();
    }

    @Named("productToId")
    default Long productToId(Product product) {
        return product.getId();
    }
}
