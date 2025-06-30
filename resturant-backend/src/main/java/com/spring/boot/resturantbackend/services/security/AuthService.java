package com.spring.boot.resturantbackend.services.security;

import com.spring.boot.resturantbackend.vm.Security.AccountAuthRequestVm;
import com.spring.boot.resturantbackend.vm.Security.AccountAuthResponseVm;
import com.spring.boot.resturantbackend.vm.Security.ProfileResponseVm;

public interface AuthService {
    AccountAuthResponseVm signUp(AccountAuthRequestVm accountAuthRequestVm);

    AccountAuthResponseVm login(AccountAuthRequestVm accountAuthRequestVm);

    ProfileResponseVm getProfile();
}
