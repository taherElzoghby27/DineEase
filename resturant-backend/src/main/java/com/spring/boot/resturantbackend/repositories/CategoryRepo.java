package com.spring.boot.resturantbackend.repositories;

import com.spring.boot.resturantbackend.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    List<Category> findAllByOrderByNameAsc();

    @Query(value = """
            SELECT c.*
                FROM hr.CATEGORY c
                JOIN (
                    SELECT p.CATEGORY_ID
                    FROM hr.ORDER_TABLE_PRODUCTS otps
                    JOIN hr.PRODUCT p ON p.ID = otps.PRODUCT_ID
                    GROUP BY p.CATEGORY_ID
                    ORDER BY COUNT(*) DESC
                    FETCH FIRST 1 ROWS ONLY
                ) pc ON c.ID = pc.CATEGORY_ID
            """,nativeQuery = true)
    Optional<Category> findCategoryForRecommendation();
}
