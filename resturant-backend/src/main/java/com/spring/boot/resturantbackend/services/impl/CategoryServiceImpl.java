package com.spring.boot.resturantbackend.services.impl;

import com.spring.boot.resturantbackend.dto.CategoryDto;
import com.spring.boot.resturantbackend.mappers.CategoryMapper;
import com.spring.boot.resturantbackend.models.Category;
import com.spring.boot.resturantbackend.repositories.CategoryRepo;
import com.spring.boot.resturantbackend.services.CategoryService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Cacheable(value = "categories", key = "'all'")
    @Override
    public List<CategoryDto> getAllCategories() {
        try {
            List<Category> categories = categoryRepo.findAllByOrderByNameAsc();
            if (categories.isEmpty()) {
                throw new SystemException("error.empty.list.category");
            }
            return categories.stream().map(CategoryMapper.CATEGORY_MAPPER::toCategoryDto).toList();
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @CacheEvict(value = "categories", key = "'all'")
    public CategoryDto createCategory(CategoryDto categoryDto) {
        try {
            if (Objects.nonNull(categoryDto.getId())) {
                throw new SystemException("id.must_be.null");
            }
            Category category = CategoryMapper.CATEGORY_MAPPER.toCategory(categoryDto);
            category = categoryRepo.save(category);
            return CategoryMapper.CATEGORY_MAPPER.toCategoryDto(category);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @CacheEvict(value = "categories", key = "'all'")
    public List<CategoryDto> createListOfCategory(List<CategoryDto> categoriesDto) {
        return saveCategoriesDto(categoriesDto);
    }

    private List<CategoryDto> saveCategoriesDto(List<CategoryDto> categoriesDto) {
        try {
            if (categoriesDto.isEmpty()) {
                throw new SystemException("error.empty.list.category");
            }
            List<Category> categories = categoriesDto.stream().map(CategoryMapper.CATEGORY_MAPPER::toCategory).toList();
            categories = categoryRepo.saveAll(categories);
            return categories.stream().map(CategoryMapper.CATEGORY_MAPPER::toCategoryDto).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @CachePut(value = "categories", key = "#result.id")
    @CacheEvict(value = "categories", key = "'all'")
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        try {
            if (Objects.isNull(categoryDto.getId())) {
                throw new SystemException("id.must_be.not_null");
            }
            Category category = CategoryMapper.CATEGORY_MAPPER.toCategory(categoryDto);
            category = categoryRepo.save(category);
            return CategoryMapper.CATEGORY_MAPPER.toCategoryDto(category);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<CategoryDto> updateListOfCategory(List<CategoryDto> categoriesDto) {
        return saveCategoriesDto(categoriesDto);
    }

    @Override

    @Caching(
            evict = {
                    @CacheEvict(value = "categories", key = "#id"),
                    @CacheEvict(value = "categories", key = "'all'")
            }
    )
    public void deleteCategoryById(Long id) {
        try {
            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }
            getCategoryById(id);
            categoryRepo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @CacheEvict(value = "categories", key = "'all'")
    public void deleteListOfCategory(List<Long> categoryIds) {
        try {
            if (categoryIds.isEmpty()) {
                throw new SystemException("error.empty.list.category");
            }
            categoryRepo.deleteAllById(categoryIds);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Cacheable(value = "categories", key = "#id")
    public CategoryDto getCategoryById(Long id) {
        try {
            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }
            Optional<Category> result = categoryRepo.findById(id);
            if (result.isEmpty()) {
                throw new SystemException("category.not.found");
            }
            return CategoryMapper.CATEGORY_MAPPER.toCategoryDto(result.get());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
