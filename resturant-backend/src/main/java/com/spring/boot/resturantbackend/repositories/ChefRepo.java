package com.spring.boot.resturantbackend.repositories;

import com.spring.boot.resturantbackend.models.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepo extends JpaRepository<Chef, Long> {
}
