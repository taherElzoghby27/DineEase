package com.spring.boot.resturantbackend.utils;

import com.spring.boot.resturantbackend.dto.security.AccountDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    private SecurityUtils() {
    }

    public static AccountDto getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("no.authenticated.user");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof AccountDto)) {
            throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
        }

        return (AccountDto) principal;
    }
}
