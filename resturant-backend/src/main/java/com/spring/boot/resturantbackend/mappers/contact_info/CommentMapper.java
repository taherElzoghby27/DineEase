package com.spring.boot.resturantbackend.mappers.contact_info;

import com.spring.boot.resturantbackend.dto.contact_info.CommentDto;
import com.spring.boot.resturantbackend.models.contact_info.Comment;
import com.spring.boot.resturantbackend.models.contact_info.ContactInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper COMMENT_MAPPER = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "contactInfo", target = "contactInfoId", qualifiedByName = "contactInfoToId")
    @Mapping(source = "contactInfo", target = "contactInfo")
    CommentDto commentToCommentDto(Comment comment);

    @Mapping(source = "contactInfo", target = "contactInfo")
    Comment commentDtoToComment(CommentDto commentDto);

    @Named("contactInfoToId")
    default Long contactInfoToId(ContactInfo contactInfo) {
        return contactInfo == null ? null : contactInfo.getId();
    }
}
