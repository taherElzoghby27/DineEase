package com.spring.boot.backend.mappers;

import com.spring.boot.backend.dto.ProductDto;
import com.spring.boot.backend.models.Product;
import com.spring.boot.backend.vm.ProductVm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE_PRODUCT = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category", target = "category")
    ProductDto toProductDto(Product product);

    ProductVm toProductVm(Product product);

    @Mapping(source = "category", target = "category")
    Product toProduct(ProductDto productDto);
}
