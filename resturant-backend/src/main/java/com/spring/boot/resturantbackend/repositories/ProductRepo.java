package com.spring.boot.resturantbackend.repositories;

import com.spring.boot.resturantbackend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query(value = "select p from Product p where p.category.id=:id")
    List<Product> findAllProductsByCategoryId(Long id);
    @Query(value = "select p from Product p where :key in (p.name,p.description)")
    List<Product> getAllProductsByKey(String key);
}
