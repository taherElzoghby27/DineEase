package com.spring.boot.resturantbackend.services.security;

import com.spring.boot.resturantbackend.dto.security.RoleDto;
import com.spring.boot.resturantbackend.models.security.RoleEntity;
import com.spring.boot.resturantbackend.vm.Security.RoleDtoVm;

import java.util.List;

public interface RoleService {
    RoleDtoVm findByRole(String role);

    List<RoleDto> update(List<RoleEntity> roles);
}
