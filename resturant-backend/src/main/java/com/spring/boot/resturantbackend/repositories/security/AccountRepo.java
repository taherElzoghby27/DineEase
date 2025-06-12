package com.spring.boot.resturantbackend.repositories.security;

import com.spring.boot.resturantbackend.models.security.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}
