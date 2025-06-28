package com.spring.boot.resturantbackend.repositories.product;

import com.spring.boot.resturantbackend.models.product.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDetailsRepo extends JpaRepository<ProductDetails, Long> {
    Optional<ProductDetails> findByProductId(Long id);
}
