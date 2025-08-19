package com.spring.boot.resturantbackend.services.impl.contact_info.impl_strategy.comment;

import com.spring.boot.resturantbackend.dto.contact_info.CommentDto;
import com.spring.boot.resturantbackend.dto.contact_info.ContactInfoDto;
import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.mappers.contact_info.CommentMapper;
import com.spring.boot.resturantbackend.mappers.contact_info.ContactInfoMapper;
import com.spring.boot.resturantbackend.models.contact_info.Comment;
import com.spring.boot.resturantbackend.models.contact_info.ContactInfo;
import com.spring.boot.resturantbackend.repositories.contact_info.CommentRepo;
import com.spring.boot.resturantbackend.services.contact_info.ContactInfoService;
import com.spring.boot.resturantbackend.services.contact_info.strategies.CommentStrategy;
import com.spring.boot.resturantbackend.services.security.AccountService;
import com.spring.boot.resturantbackend.utils.enums.FilterContactInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCommentStrategy implements CommentStrategy {
    private final CommentRepo commentRepo;
    private final ContactInfoService contactInfoService;
    private final AccountService accountService;


    @Override
    @Transactional
    public void sendComment(CommentDto commentDto) {
        Comment comment = CommentMapper.COMMENT_MAPPER.commentDtoToComment(commentDto);
        //admin to user
        AccountDto accountDto = accountService.getAccountById(commentDto.getReceiverId());
        ContactInfoDto contactInfoDto = contactInfoService.getContactInfoByIdAndAccountId(commentDto.getContactInfoId(), accountDto.getId());
        if (contactInfoDto.getStatus() != FilterContactInfo.REPLIED) {
            contactInfoService.updateStatus(FilterContactInfo.REPLIED, contactInfoDto.getId());
        }
        ContactInfo contactInfo = ContactInfoMapper.CONTACT_INFO_MAPPER.toContactInfo(contactInfoDto);
        saveCommentInDb(contactInfo, comment);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    protected void saveCommentInDb(ContactInfo contactInfo, Comment comment) {
        List<Comment> comments = contactInfo.getComment();
        Long orderNumber = (long) comments.size() + 1;
        comment.setOrderNumber(orderNumber);
        comment.setContactInfo(contactInfo);
        comments.add(comment);
        commentRepo.save(comment);
    }
}
