package com.spring.boot.resturantbackend.services.contact_info.strategies;

import com.spring.boot.resturantbackend.dto.contact_info.CommentDto;

public interface CommentStrategy {
    void sendComment(CommentDto commentDto);
}
