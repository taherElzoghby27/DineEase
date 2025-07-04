package com.spring.boot.resturantbackend.repositories.security;

import com.spring.boot.resturantbackend.models.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String role);

    @Query(value = "SELECT * FROM hr.ROLE r JOIN hr.ACCOUNT_ROLES ar ON ar.ACCOUNT_ID =:id AND r.ID =ar.ROLE_ID", nativeQuery = true)
    Optional<List<Role>> findRoleByAccountId(Long id);
}
