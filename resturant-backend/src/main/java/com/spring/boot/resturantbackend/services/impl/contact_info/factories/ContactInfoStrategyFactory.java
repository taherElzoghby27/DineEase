package com.spring.boot.resturantbackend.services.impl.contact_info.factories;

import com.spring.boot.resturantbackend.services.contact_info.strategies.ContactInfoRetrievalStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ContactInfoStrategyFactory {
    private final Map<String, ContactInfoRetrievalStrategy> strategies;

    public ContactInfoStrategyFactory(
            @Qualifier("userServiceContactInfo") ContactInfoRetrievalStrategy userService,
            @Qualifier("adminServiceContactInfo") ContactInfoRetrievalStrategy adminService
    ) {
        this.strategies = Map.of(
                "ADMIN", adminService,
                "USER", userService
        );
    }

    public ContactInfoRetrievalStrategy getStrategy(String role) {
        return this.strategies.get(role);
    }
}
