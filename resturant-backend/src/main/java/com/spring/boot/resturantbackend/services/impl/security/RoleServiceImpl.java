package com.spring.boot.resturantbackend.services.impl.security;

import com.spring.boot.resturantbackend.dto.security.RoleDto;
import com.spring.boot.resturantbackend.mappers.security.RoleMapper;
import com.spring.boot.resturantbackend.models.security.Role;
import com.spring.boot.resturantbackend.repositories.security.RoleRepo;
import com.spring.boot.resturantbackend.services.security.RoleService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public RoleDto findByRole(String role) {
        try {
            Optional<Role> roleResult = roleRepo.findByRole(role);
            if (roleResult.isEmpty()) {
                throw new SystemException("error.role.not.found");
            }
            return RoleMapper.ROLE_MAPPER.toRoleDto(roleResult.get());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<RoleDto> getRoleByAccountId(Long id) {
        try {
            Optional<List<Role>> roleResult = roleRepo.findRoleByAccountId(id);
            if (roleResult.isEmpty()) {
                throw new SystemException("error.role.not.found");
            }
            return roleResult.get().stream().map(RoleMapper.ROLE_MAPPER::toRoleDto).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
