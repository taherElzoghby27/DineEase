package com.spring.boot.resturantbackend.services.security;

import com.spring.boot.resturantbackend.dto.security.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto findByRole(String role);

    List<RoleDto> getRoleByAccountId(Long id);
}
