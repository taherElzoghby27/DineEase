package com.spring.boot.resturantbackend.services.impl.contact_info;

import com.spring.boot.resturantbackend.dto.contact_info.CommentDto;
import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.mappers.contact_info.CommentMapper;
import com.spring.boot.resturantbackend.models.contact_info.Comment;
import com.spring.boot.resturantbackend.repositories.contact_info.CommentRepo;
import com.spring.boot.resturantbackend.services.contact_info.CommentService;
import com.spring.boot.resturantbackend.services.security.AccountService;
import com.spring.boot.resturantbackend.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private AccountService accountService;

    @Override
    public void sendComment(CommentDto commentDto) {
        if (Objects.isNull(commentDto.getReceiverId())) {
            //from user to admin
            Comment comment = CommentMapper.COMMENT_MAPPER.commentDtoToComment(commentDto);

            AccountDto accountDto = SecurityUtils.getCurrentAccount();
        } else {
            //from admin to user
        }
    }
}
