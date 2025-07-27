package com.spring.boot.resturantbackend.utils;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("accountIdGenerator")
public class AccountIdKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        // Get the current account ID from your security utility
        Long accountId;
        try {
            accountId = SecurityUtils.getCurrentAccount().getId();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to get account ID", e);
        }

        return accountId;
    }
}
