package com.spring.boot.backend.mappers;

import com.spring.boot.backend.dto.CategoryDto;
import com.spring.boot.backend.models.Category;
import com.spring.boot.backend.vm.CategoryVm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE_CATEGORY = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "products", target = "products")
    CategoryDto toCategoryDto(Category category);

    CategoryVm toCategoryVm(Category category);

    @Mapping(source = "products", target = "products")
    Category toCategory(CategoryDto categoryDto);
}
