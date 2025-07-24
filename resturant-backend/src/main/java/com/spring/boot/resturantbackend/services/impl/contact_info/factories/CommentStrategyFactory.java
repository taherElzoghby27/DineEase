package com.spring.boot.resturantbackend.services.impl.contact_info.factories;

import com.spring.boot.resturantbackend.services.contact_info.strategies.CommentStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommentStrategyFactory {
    private final Map<String, CommentStrategy> strategies;

    public CommentStrategyFactory(@Qualifier("userCommentStrategy") CommentStrategy userStrategy,
                                  @Qualifier("adminCommentStrategy") CommentStrategy adminStrategy) {
        this.strategies = Map.of(
                "USER", userStrategy, "ADMIN", adminStrategy
        );
    }

    public CommentStrategy getStrategy(String role) {
        return this.strategies.get(role);
    }
}
