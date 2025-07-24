package com.spring.boot.resturantbackend.services.impl.contact_info;

import com.spring.boot.resturantbackend.dto.contact_info.CommentDto;
import com.spring.boot.resturantbackend.services.contact_info.CommentService;
import com.spring.boot.resturantbackend.services.impl.contact_info.factories.CommentStrategyFactory;
import com.spring.boot.resturantbackend.utils.SecurityUtils;
import com.spring.boot.resturantbackend.utils.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentStrategyFactory commentStrategyFactory;

    @Override
    public void sendComment(CommentDto commentDto) {
        boolean isAdmin = SecurityUtils.hasRole(RoleEnum.ADMIN);
        RoleEnum role = isAdmin ? RoleEnum.ADMIN : RoleEnum.USER;
        commentStrategyFactory.getStrategy(role.toString()).sendComment(commentDto);
    }
}
