package com.spring.boot.resturantbackend.services.contact_info;

import com.spring.boot.resturantbackend.dto.contact_info.CommentDto;

public interface CommentService {
    void sendComment(CommentDto commentDto);
}
