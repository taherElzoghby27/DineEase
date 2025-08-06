package com.spring.boot.resturantbackend.services.impl.security;

import com.spring.boot.resturantbackend.config.security.TokenHandler;
import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.exceptions.BadRequestException;
import com.spring.boot.resturantbackend.exceptions.InvalidCredentialsException;
import com.spring.boot.resturantbackend.exceptions.NotFoundResourceException;
import com.spring.boot.resturantbackend.mappers.security.AccountMapper;
import com.spring.boot.resturantbackend.services.security.AccountService;
import com.spring.boot.resturantbackend.services.security.AuthService;
import com.spring.boot.resturantbackend.utils.SecurityUtils;
import com.spring.boot.resturantbackend.vm.Security.AccountAuthRequestVm;
import com.spring.boot.resturantbackend.vm.Security.AccountAuthResponseVm;
import com.spring.boot.resturantbackend.vm.Security.ChangePasswordRequest;
import com.spring.boot.resturantbackend.vm.Security.UpdateProfileVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private TokenHandler tokenHandler;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AccountAuthResponseVm signUp(AccountAuthRequestVm accountAuthRequestVm) {
        AccountDto accountDto = AccountMapper.ACCOUNT_MAPPER.toAccountDto(accountAuthRequestVm);
        accountDto = accountService.createAccount(accountDto);
        AccountAuthResponseVm accountAuthResponseVm = AccountMapper.ACCOUNT_MAPPER.toAccountResponseVm(accountDto);
        accountAuthResponseVm.setToken(tokenHandler.generateToken(accountDto));
        return accountAuthResponseVm;
    }

    @Override
    public AccountAuthResponseVm login(AccountAuthRequestVm accountAuthRequestVm) {
        AccountDto accountDto = accountService.getAccountByUsername(accountAuthRequestVm.getUsername());
        if (Objects.isNull(accountDto)) {
            throw new NotFoundResourceException("not_found.account");
        }
        if (!passwordEncoder.matches(accountAuthRequestVm.getPassword(), accountDto.getPassword())) {
            throw new InvalidCredentialsException("error.invalid.credentials");
        }
        AccountAuthResponseVm accountAuthResponseVm = AccountMapper.ACCOUNT_MAPPER.toAccountResponseVm(accountDto);
        accountAuthResponseVm.setToken(tokenHandler.generateToken(accountDto));
        return accountAuthResponseVm;
    }

    @Override
    public UpdateProfileVm getProfile() {
        AccountDto accountDto = getUserDataFromContext();
        accountDto = accountService.getAccountById(accountDto.getId());
        return AccountMapper.ACCOUNT_MAPPER.toProfileResponseVm(accountDto);
    }

    @Override
    @Transactional
    public UpdateProfileVm updateProfile(UpdateProfileVm updateProfileVm) {
        AccountDto accountDto = getUserDataFromContext();
        if (Objects.isNull(accountDto)) {
            throw new NotFoundResourceException("not_found.account");
        }
        updateProfileVm = accountService.updateAccount(updateProfileVm);
        return updateProfileVm;
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        //verify username and password
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        if (!changePasswordRequest.getUsername().equals(accountDto.getUsername()) ||
            !passwordEncoder.matches(changePasswordRequest.getOldPassword(), accountDto.getPassword())) {
            throw new InvalidCredentialsException("username.or.password.incorrect");
        }
        accountService.changePassword(changePasswordRequest);
    }

    private AccountDto getUserDataFromContext() {
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        accountDto = validateAccountDto(accountDto);
        return accountDto;
    }

    private AccountDto validateAccountDto(AccountDto accountDto) {
        if (Objects.isNull(accountDto.getId())) {
            throw new BadRequestException("id.must_be.not_null");
        }
        accountDto = accountService.getAccountById(accountDto.getId());
        if (Objects.isNull(accountDto)) {
            throw new NotFoundResourceException("not_found.account");
        }
        return accountDto;
    }
}
