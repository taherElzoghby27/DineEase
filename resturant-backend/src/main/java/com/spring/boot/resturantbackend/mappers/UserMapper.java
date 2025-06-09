package com.spring.boot.resturantbackend.mappers;

import com.spring.boot.resturantbackend.dto.security.UserDto;
import com.spring.boot.resturantbackend.models.security.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(UserEntity userEntity);

    UserEntity toUserEntity(UserDto userDto);
}
