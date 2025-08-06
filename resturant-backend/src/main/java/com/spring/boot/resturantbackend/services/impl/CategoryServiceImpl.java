package com.spring.boot.resturantbackend.services.impl;

import com.spring.boot.resturantbackend.dto.CategoryDto;
import com.spring.boot.resturantbackend.exceptions.BadRequestException;
import com.spring.boot.resturantbackend.exceptions.NotFoundResourceException;
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
import org.springframework.transaction.annotation.Transactional;

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
        List<Category> categories = categoryRepo.findAllByOrderByNameAsc();
        if (categories.isEmpty()) {
            throw new NotFoundResourceException("error.empty.list.category");
        }
        return categories.stream().map(CategoryMapper.CATEGORY_MAPPER::toCategoryDto).toList();
    }

    @Override
    @CacheEvict(value = "categories", key = "'all'")
    @CachePut(value = "categories", key = "#result.id")
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (Objects.nonNull(categoryDto.getId())) {
            throw new BadRequestException("id.must_be.null");
        }
        Category category = CategoryMapper.CATEGORY_MAPPER.toCategory(categoryDto);
        category.setRecommended(0L);
        category = categoryRepo.save(category);
        return CategoryMapper.CATEGORY_MAPPER.toCategoryDto(category);
    }

    @Override
    @CacheEvict(value = "categories", key = "'all'")
    @Transactional
    public List<CategoryDto> createListOfCategory(List<CategoryDto> categoriesDto) {
        if (categoriesDto.isEmpty()) {
            throw new NotFoundResourceException("error.empty.list.category");
        }
        List<Category> categories = categoriesDto.stream().map(CategoryMapper.CATEGORY_MAPPER::toCategory).toList();
        categories = categoryRepo.saveAll(categories);
        return categories.stream().map(CategoryMapper.CATEGORY_MAPPER::toCategoryDto).toList();
    }

    @Override
    @CachePut(value = "categories", key = "#result.id")
    @CacheEvict(value = "categories", key = "'all'")
    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        if (Objects.isNull(categoryDto.getId())) {
            throw new BadRequestException("id.must_be.not_null");
        }
        Category category = CategoryMapper.CATEGORY_MAPPER.toCategory(categoryDto);
        category = categoryRepo.save(category);
        return CategoryMapper.CATEGORY_MAPPER.toCategoryDto(category);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "categories", key = "#id"), @CacheEvict(value = "categories", key = "'all'")})
    @Transactional
    public void deleteCategoryById(Long id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("id.must_be.not_null");
        }
        getCategoryById(id);
        categoryRepo.deleteById(id);
    }


    @Override
    @Cacheable(value = "categories", key = "#id")
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("id.must_be.not_null");
        }
        Optional<Category> result = categoryRepo.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundResourceException("category.not.found");
        }
        return CategoryMapper.CATEGORY_MAPPER.toCategoryDto(result.get());
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true, condition = "#result")
    @Transactional
    public boolean updateRecommendedCategory() {//update category recommended flag based on n of products in orders
        //get category based on n of products in orders
        CategoryDto recommendedCategory = getCategoryBasedOnProducts();
        Long recommendedId = recommendedCategory.getId();
        //get all categories
        List<Category> categories = categoryRepo.findAll();
        boolean hasChanges = false;
        //update non-recommended category if recommended
        for (Category category : categories) {
            boolean shouldBeRecommended = category.getId().equals(recommendedId);
            long targetValue = shouldBeRecommended ? 1L : 0L;

            if (!Objects.equals(category.getRecommended(), targetValue)) {
                category.setRecommended(targetValue);
                hasChanges = true;
            }
        }
        if (hasChanges) {
            categoryRepo.saveAll(categories);
        }
        return hasChanges;
    }


    private CategoryDto getCategoryBasedOnProducts() {
        Optional<Category> result = categoryRepo.findCategoryForRecommendation();
        if (result.isEmpty()) {
            throw new NotFoundResourceException("category.not.found");
        }
        return CategoryMapper.CATEGORY_MAPPER.toCategoryDto(result.get());
    }
}
