package com.spring.boot.resturantbackend.config;

import com.spring.boot.resturantbackend.dto.security.AccountDto;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // In production, use Spring Security to get actual username
        // Example: return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
        AccountDto acc = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(acc.getUsername());// Default user
    }
}
