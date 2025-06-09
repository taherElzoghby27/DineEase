package com.spring.boot.resturantbackend.services.impl;

import com.spring.boot.resturantbackend.config.security.TokenHandler;
import com.spring.boot.resturantbackend.dto.security.RoleDto;
import com.spring.boot.resturantbackend.dto.security.UserDto;
import com.spring.boot.resturantbackend.mappers.RoleMapper;
import com.spring.boot.resturantbackend.mappers.UserMapper;
import com.spring.boot.resturantbackend.services.AccountService;
import com.spring.boot.resturantbackend.services.AuthService;
import com.spring.boot.resturantbackend.services.RoleService;
import com.spring.boot.resturantbackend.utils.RoleEnum;
import com.spring.boot.resturantbackend.vm.Security.UserAuthRequestVm;
import com.spring.boot.resturantbackend.vm.Security.UserAuthResponseVm;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TokenHandler tokenHandler;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserAuthResponseVm signUp(UserAuthRequestVm userAuthRequestVm) throws SystemException {
        UserDto userDto = UserMapper.USER_MAPPER.toUserDto(userAuthRequestVm);
        RoleDto roleDto = RoleMapper.ROLE_MAPPER.toRoleDto(roleService.findByRole(RoleEnum.USER.toString()));
        userDto.setRoles(List.of(roleDto));
        userDto = accountService.createAccount(userDto);
        UserAuthResponseVm userAuthResponseVm = UserMapper.USER_MAPPER.toUserResponseVm(userDto);
        userAuthResponseVm.setToken(tokenHandler.generateToken(userDto));
        return userAuthResponseVm;
    }

    @Override
    public UserAuthResponseVm login(UserAuthRequestVm userAuthRequestVm) throws SystemException {
        UserDto userDto = accountService.getAccountByUsername(userAuthRequestVm.getUsername());
        if (!passwordEncoder.matches(userAuthRequestVm.getPassword(), userDto.getPassword())) {
            throw new SystemException("error.invalid.credentials");
        }
        UserAuthResponseVm userAuthResponseVm = UserMapper.USER_MAPPER.toUserResponseVm(userDto);
        userAuthResponseVm.setToken(tokenHandler.generateToken(userDto));
        return userAuthResponseVm;
    }
}
