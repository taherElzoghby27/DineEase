package com.spring.boot.resturantbackend.repositories.product;

import com.spring.boot.resturantbackend.models.product.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepo extends JpaRepository<ProductDetails, Long> {
}
