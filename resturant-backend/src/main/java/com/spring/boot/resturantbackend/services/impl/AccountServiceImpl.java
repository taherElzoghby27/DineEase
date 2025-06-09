package com.spring.boot.resturantbackend.services.impl;

import com.spring.boot.resturantbackend.dto.security.UserDto;
import com.spring.boot.resturantbackend.mappers.UserMapper;
import com.spring.boot.resturantbackend.models.security.UserEntity;
import com.spring.boot.resturantbackend.repositories.AccountRepo;
import com.spring.boot.resturantbackend.services.AccountService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepo accountRepo;

    @Override
    public List<UserDto> getAccounts() throws SystemException {
        List<UserEntity> users = accountRepo.findAll();
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
            throw new SystemException("user.exists");
        }
        UserEntity user = UserMapper.USER_MAPPER.toUserEntity(userDto);
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
        UserEntity user = UserMapper.USER_MAPPER.toUserEntity(userDto);
        user = accountRepo.save(user);
        return UserMapper.USER_MAPPER.toUserDto(user);
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
        Optional<UserEntity> result = accountRepo.findById(id);
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
        Optional<UserEntity> result = accountRepo.findByUsername(username);
        if (result.isEmpty()) {
            throw new SystemException("not_found.account");
        }
        return UserMapper.USER_MAPPER.toUserDto(result.get());
    }
}
