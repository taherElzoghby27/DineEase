package com.spring.boot.backend.services.impl;

import com.spring.boot.backend.dto.CategoryDto;
import com.spring.boot.backend.mappers.CategoryMapper;
import com.spring.boot.backend.models.Category;
import com.spring.boot.backend.repositories.CategoryRepo;
import com.spring.boot.backend.services.CategoryService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        return categories.stream().map(CategoryMapper.INSTANCE_CATEGORY::toCategoryDto).toList();
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) throws SystemException {
        try {
            if (Objects.nonNull(categoryDto.getId())) {
                throw new SystemException("id.must_be.null");
            }
            Category category = CategoryMapper.INSTANCE_CATEGORY.toCategory(categoryDto);
            category = categoryRepo.save(category);
            return CategoryMapper.INSTANCE_CATEGORY.toCategoryDto(category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SystemException("something.error");
        }
    }

    @Override
    public List<CategoryDto> createListOfCategory(List<CategoryDto> categoryDto) throws SystemException {
        try {
            if (categoryDto.isEmpty()) {
                throw new SystemException("error.empty.list.category");
            }
            List<Category> categories = categoryDto.stream().map(CategoryMapper.INSTANCE_CATEGORY::toCategory).toList();
            categories = categoryRepo.saveAll(categories);
            return categories.stream().map(CategoryMapper.INSTANCE_CATEGORY::toCategoryDto).toList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SystemException("something.error");
        }
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) throws SystemException {
        try {
            if (Objects.isNull(categoryDto.getId())) {
                throw new SystemException("id.must_be.not_null");
            }
            Category category = CategoryMapper.INSTANCE_CATEGORY.toCategory(categoryDto);
            category = categoryRepo.save(category);
            categoryDto = CategoryMapper.INSTANCE_CATEGORY.toCategoryDto(category);
            return categoryDto;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SystemException("something.error");
        }
    }

    @Override
    public List<CategoryDto> updateListOfCategory(List<CategoryDto> categoryDto) throws SystemException {
        try {
            if (categoryDto.isEmpty()) {
                throw new SystemException("error.empty.list.category");
            }
            List<Category> categories = categoryDto.stream().map(CategoryMapper.INSTANCE_CATEGORY::toCategory).toList();
            categories = categoryRepo.saveAll(categories);
            return categories.stream().map(CategoryMapper.INSTANCE_CATEGORY::toCategoryDto).toList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SystemException("something.error");
        }
    }

    @Override
    public void deleteCategoryById(Long id) throws SystemException {
        try {
            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }
            getCategoryById(id);
            categoryRepo.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SystemException("something.error");
        }
    }

    @Override
    public void deleteListOfCategory(List<Long> categoryIds) throws SystemException {
        try {
            if (categoryIds.isEmpty()) {
                throw new SystemException("error.empty.list.category");
            }
            categoryRepo.deleteAllById(categoryIds);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SystemException("something.error");
        }
    }

    @Override
    public CategoryDto getCategoryById(Long id) throws SystemException {
        try {
            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }
            Optional<Category> result = categoryRepo.findById(id);
            if (result.isEmpty()) {
                throw new SystemException("category.not.found");
            }
            return CategoryMapper.INSTANCE_CATEGORY.toCategoryDto(result.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SystemException("something.error");
        }
    }
}
