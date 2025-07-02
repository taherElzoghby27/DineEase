package com.spring.boot.resturantbackend.services.security;

import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.models.security.Account;
import com.spring.boot.resturantbackend.vm.Security.UpdateProfileVm;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAccounts();

    AccountDto createAccount(AccountDto accountDto);

    UpdateProfileVm updateAccount(UpdateProfileVm updateProfileVm);

    void deleteAccount(Long id);

    AccountDto getAccountById(Long id);

    AccountDto getAccountByUsername(String username);

    Account idToAccount(Long id);

}
