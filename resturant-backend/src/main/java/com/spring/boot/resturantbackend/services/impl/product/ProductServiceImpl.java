package com.spring.boot.resturantbackend.services.impl.product;

import com.spring.boot.resturantbackend.controllers.vm.ProductResponseVm;
import com.spring.boot.resturantbackend.dto.CategoryDto;
import com.spring.boot.resturantbackend.dto.product.ProductDto;
import com.spring.boot.resturantbackend.mappers.CategoryMapper;
import com.spring.boot.resturantbackend.mappers.ProductMapper;
import com.spring.boot.resturantbackend.models.Category;
import com.spring.boot.resturantbackend.models.product.Product;
import com.spring.boot.resturantbackend.repositories.product.ProductRepo;
import com.spring.boot.resturantbackend.services.CategoryService;
import com.spring.boot.resturantbackend.services.product.ProductService;
import com.spring.boot.resturantbackend.vm.product.ProductRequestVm;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryService categoryService;

    @Override
    @Cacheable(value = "products", key = "'products'+#page +'-'+#size")
    public ProductResponseVm getAllProducts(int page, int size) {
        try {
            Pageable pageable = getPageable(page, size);
            Page<Product> result = productRepo.findAllByOrderByIdAsc(pageable);
            if (result.isEmpty()) {
                throw new SystemException("products.not.found");
            }
            return new ProductResponseVm(
                    result.getContent().stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList(),
                    result.getTotalElements()
            );
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Cacheable(value = "products", key = "'products category id '+#id+'-'+#page +'-'+#size")
    public ProductResponseVm getAllProductsByCategoryId(Long id, int page, int size) {
        try {
            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }
            Pageable pageable = getPageable(page, size);
            Page<Product> result = productRepo.findAllProductsByCategoryIdByOrderByIdAsc(id, pageable);
            if (result.isEmpty()) {
                throw new SystemException("products.category.not.found");
            }
            return new ProductResponseVm(
                    result.getContent().stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList(),
                    result.getTotalElements()
            );
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    @CachePut(value = "products", key = "#result.id")
    public ProductRequestVm createProduct(ProductRequestVm productRequestVm) {
        try {
            if (Objects.nonNull(productRequestVm.getId())) {
                throw new SystemException("id.must_be.null");
            }
            if (Objects.isNull(productRequestVm.getCategoryId())) {
                throw new SystemException("id.must_be.not_null");
            }
            //map productRequestVm to product
            Product product = ProductMapper.PRODUCT_MAPPER.toProduct(productRequestVm);
            //get category by id
            CategoryDto categoryDto = categoryService.getCategoryById(productRequestVm.getCategoryId());
            //map categoryDto to category
            Category category = CategoryMapper.CATEGORY_MAPPER.toCategory(categoryDto);
            product.setCategory(category);
            product = productRepo.save(product);
            return ProductMapper.PRODUCT_MAPPER.toProductRequestVm(product);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public List<ProductDto> createListOfProduct(List<ProductDto> productDto) {
        try {
            if (productDto.isEmpty()) {
                throw new SystemException("error.empty.list.product");
            }
            List<Product> products = productDto.stream().map(ProductMapper.PRODUCT_MAPPER::toProduct).toList();
            products = productRepo.saveAll(products);
            return products.stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    @CachePut(value = "products", key = "#result.id")
    public ProductDto updateProduct(ProductDto productDto) {
        try {
            if (Objects.isNull(productDto.getId())) {
                throw new SystemException("id.must_be.not_null");
            }
            Product product = ProductMapper.PRODUCT_MAPPER.toProduct(productDto);
            product = productRepo.save(product);
            return ProductMapper.PRODUCT_MAPPER.toProductDto(product);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public List<ProductDto> updateListOfProduct(List<ProductDto> productDto) {
        try {
            if (productDto.isEmpty()) {
                throw new SystemException("error.empty.list.category");
            }
            List<Product> products = productDto.stream().map(ProductMapper.PRODUCT_MAPPER::toProduct).toList();
            products = productRepo.saveAll(products);
            return products.stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public void deleteProductById(Long id) {
        try {
            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }
            getProductById(id);
            productRepo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public void deleteListOfProduct(List<Long> productIds) {
        try {
            if (productIds.isEmpty()) {
                throw new SystemException("error.empty.list.category");
            }
            productRepo.deleteAllById(productIds);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public ProductDto getProductById(Long id) {
        try {
            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }
            Optional<Product> result = productRepo.findById(id);
            if (result.isEmpty()) {
                throw new SystemException("products.not.found");
            }
            return ProductMapper.PRODUCT_MAPPER.toProductDto(result.get());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductResponseVm getAllProductsByKey(String key, int page, int size) {
        try {
            if (Objects.isNull(key)) {
                throw new SystemException("key.null");
            }
            Pageable pageable = getPageable(page, size);
            Page<Product> result = productRepo.getAllProductsByKeyByOrderByIdAsc(key, pageable);
            if (result.isEmpty()) {
                throw new SystemException("products.not.found");
            }
            return new ProductResponseVm(
                    result.getContent().stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList(),
                    result.getTotalElements()
            );
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductResponseVm getAllProductsByCategoryIdAndKey(Long categoryId, String key, int page, int size) {
        try {
            if (Objects.isNull(key)) {
                throw new SystemException("key.null");
            }
            categoryService.getCategoryById(categoryId);
            Pageable pageable = getPageable(page, size);
            Page<Product> result = productRepo.getAllProductsByKeyByCategoryIdByOrderByIdAsc(categoryId, key, pageable);
            if (result.isEmpty()) {
                throw new SystemException("products.not.found");
            }
            return new ProductResponseVm(
                    result.getContent().stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList(),
                    result.getTotalElements()
            );
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ProductDto> getProductsByListOfId(List<Long> productIds) {
        try {
            List<Product> products = productRepo.findAllById(productIds);
            if (products.isEmpty()) {
                throw new SystemException("products.not.found");
            }
            return products.stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList();
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Product> listOfIdsToListOfProducts(List<Long> ids) {
        List<ProductDto> productsDto = getProductsByListOfId(ids);
        return productsDto.stream().map(ProductMapper.PRODUCT_MAPPER::toProduct).toList();
    }

    private static Pageable getPageable(int page, int size) {
        try {
            if (page < 1) {
                throw new SystemException("error.min.one.page");
            }
            return PageRequest.of(page - 1, size);
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
