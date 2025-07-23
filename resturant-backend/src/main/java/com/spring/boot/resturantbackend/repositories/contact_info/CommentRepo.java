package com.spring.boot.resturantbackend.repositories.contact_info;

import com.spring.boot.resturantbackend.models.contact_info.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
}
