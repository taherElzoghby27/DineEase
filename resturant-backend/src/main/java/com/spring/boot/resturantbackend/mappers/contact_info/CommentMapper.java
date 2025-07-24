package com.spring.boot.resturantbackend.mappers.contact_info;

import com.spring.boot.resturantbackend.dto.contact_info.CommentDto;
import com.spring.boot.resturantbackend.models.contact_info.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper COMMENT_MAPPER = Mappers.getMapper(CommentMapper.class);

    CommentDto commentToCommentDto(Comment comment);

    Comment commentDtoToComment(CommentDto commentDto);
}
