package com.spring.boot.resturantbackend.services.security;

import com.spring.boot.resturantbackend.vm.Security.AccountAuthRequestVm;
import com.spring.boot.resturantbackend.vm.Security.AccountAuthResponseVm;
import com.spring.boot.resturantbackend.vm.Security.UpdateProfileVm;

public interface AuthService {
    AccountAuthResponseVm signUp(AccountAuthRequestVm accountAuthRequestVm);

    AccountAuthResponseVm login(AccountAuthRequestVm accountAuthRequestVm);

    UpdateProfileVm getProfile();

    UpdateProfileVm updateProfile(UpdateProfileVm updateProfileVm);
}
