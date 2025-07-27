package com.spring.boot.resturantbackend.services.impl.order;

import com.spring.boot.resturantbackend.services.order.OrderAccessStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderStrategyFactory {
    private final Map<String, OrderAccessStrategy> strategies;

    public OrderStrategyFactory(
            @Qualifier("userOrders") OrderAccessStrategy userStrategy,
            @Qualifier("adminOrders") OrderAccessStrategy adminStrategy
    ) {
        this.strategies = Map.of(
                "ADMIN", adminStrategy,
                "USER", userStrategy
        );
    }

    public OrderAccessStrategy getStrategy(String role) {
        return this.strategies.get(role);
    }
}
