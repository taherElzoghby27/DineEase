package com.spring.boot.resturantbackend.services.impl.security;

import com.spring.boot.resturantbackend.dto.security.RoleDto;
import com.spring.boot.resturantbackend.dto.security.UserDto;
import com.spring.boot.resturantbackend.mappers.security.RoleMapper;
import com.spring.boot.resturantbackend.mappers.security.UserMapper;
import com.spring.boot.resturantbackend.models.security.Role;
import com.spring.boot.resturantbackend.models.security.Users;
import com.spring.boot.resturantbackend.repositories.security.AccountRepo;
import com.spring.boot.resturantbackend.services.security.AccountService;
import com.spring.boot.resturantbackend.services.security.RoleService;
import com.spring.boot.resturantbackend.utils.RoleEnum;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    @Override
    public List<UserDto> getAccounts() throws SystemException {
        List<Users> users = accountRepo.findAll();
        if (users.isEmpty()) {
            throw new SystemException("empty.accounts");
        }
        return users.stream().map(UserMapper.USER_MAPPER::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto createAccount(UserDto userDto) throws SystemException {
        if (Objects.nonNull(userDto.getId())) {
            throw new SystemException("id.must_be.null");
        }
        if (Objects.nonNull(getAccountByUsername(userDto.getUsername()))) {
            throw new SystemException("users.exists");
        }
        //enable account
        userDto.setEnabled("1");
        Users user = UserMapper.USER_MAPPER.toUserEntity(userDto);
        //encode password
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        //make relation between user and role
        Role role = RoleMapper.ROLE_MAPPER.toRole(roleService.findByRole(RoleEnum.USER.toString()));
        List<Role> roles = user.getRoles();
        if (Objects.isNull(roles)) {
            roles = new ArrayList<>();
        }
        roles.add(role);
        user.setRoles(roles);
        user = accountRepo.save(user);
        return UserMapper.USER_MAPPER.toUserDto(user);
    }

    @Override
    public UserDto updateAccount(UserDto userDto) throws SystemException {
        if (Objects.isNull(userDto.getId())) {
            throw new SystemException("id.must_be.not_null");
        }
        if (Objects.isNull(getAccountById(userDto.getId()))) {
            throw new SystemException("not_found.account");
        }
        Users users = UserMapper.USER_MAPPER.toUserEntity(userDto);
        users = accountRepo.save(users);
        return UserMapper.USER_MAPPER.toUserDto(users);
    }

    @Override
    public void deleteAccount(Long id) throws SystemException {
        if (Objects.isNull(id)) {
            throw new SystemException("id.must_be.not_null");
        }
        if (Objects.isNull(getAccountById(id))) {
            throw new SystemException("not_found.account");
        }
        accountRepo.deleteById(id);
    }

    @Override
    public UserDto getAccountById(Long id) throws SystemException {
        if (Objects.isNull(id)) {
            throw new SystemException("id.must_be.not_null");
        }
        Optional<Users> result = accountRepo.findById(id);
        if (result.isEmpty()) {
            throw new SystemException("not_found.account");
        }
        return UserMapper.USER_MAPPER.toUserDto(result.get());
    }

    @Override
    public UserDto getAccountByUsername(String username) throws SystemException {
        if (username.isEmpty()) {
            throw new SystemException("not_empty.name");
        }
        Optional<Users> result = accountRepo.findByUsername(username);
        if (result.isEmpty()) {
            return null;
        }
        return UserMapper.USER_MAPPER.toUserDto(result.get());
    }
}
