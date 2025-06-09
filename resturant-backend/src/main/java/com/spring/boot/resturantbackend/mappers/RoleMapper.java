package com.spring.boot.resturantbackend.mappers;

import com.spring.boot.resturantbackend.dto.security.RoleDto;
import com.spring.boot.resturantbackend.models.security.RoleEntity;
import com.spring.boot.resturantbackend.vm.RoleDtoVm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper ROLE_MAPPER = Mappers.getMapper(RoleMapper.class);

    RoleDtoVm toRoleDtoVm(RoleEntity role);

    RoleEntity toRole(RoleDto roleDto);

    RoleDto toRoleDto(RoleEntity role);
}