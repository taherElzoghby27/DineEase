package com.spring.boot.resturantbackend.services.security;

import com.spring.boot.resturantbackend.dto.security.RoleDto;
import com.spring.boot.resturantbackend.models.security.Role;

import java.util.List;

public interface RoleService {
    RoleDto findByRole(String role);

    List<RoleDto> update(List<Role> roles);
}
