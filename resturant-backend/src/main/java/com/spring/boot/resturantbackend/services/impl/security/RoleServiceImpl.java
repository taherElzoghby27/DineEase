package com.spring.boot.resturantbackend.services.impl.security;

import com.spring.boot.resturantbackend.dto.security.RoleDto;
import com.spring.boot.resturantbackend.exceptions.NotFoundResourceException;
import com.spring.boot.resturantbackend.mappers.security.RoleMapper;
import com.spring.boot.resturantbackend.models.security.Role;
import com.spring.boot.resturantbackend.repositories.security.RoleRepo;
import com.spring.boot.resturantbackend.services.security.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;

    @Override
    public RoleDto findByRole(String role) {
        Optional<Role> roleResult = roleRepo.findByRole(role);
        if (roleResult.isEmpty()) {
            throw new NotFoundResourceException("error.role.not.found");
        }
        return RoleMapper.ROLE_MAPPER.toRoleDto(roleResult.get());
    }

    @Override
    public List<RoleDto> getRoleByAccountId(Long id) {
        Optional<List<Role>> roleResult = roleRepo.findRoleByAccountId(id);
        if (roleResult.isEmpty()) {
            throw new NotFoundResourceException("error.role.not.found");
        }
        return roleResult.get().stream().map(RoleMapper.ROLE_MAPPER::toRoleDto).toList();
    }
}
