package com.spring.boot.resturantbackend.repositories;

import com.spring.boot.resturantbackend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByIdAsc();

    @Query(value = "select p from Product p where p.category.id=:id order by p.id")
    List<Product> findAllProductsByCategoryIdByOrderByIdAsc(Long id);

    @Query("SELECT p FROM Product p WHERE lower(p.name) LIKE lower(concat('%', :key, '%')) OR lower(p.description) LIKE lower(concat('%', :key, '%')) ORDER BY p.id")
    List<Product> getAllProductsByKeyByOrderByIdAsc(@Param("key") String key);
}
