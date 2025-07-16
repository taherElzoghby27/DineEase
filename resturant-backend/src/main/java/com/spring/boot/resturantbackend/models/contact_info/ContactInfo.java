package com.spring.boot.resturantbackend.models.contact_info;

import com.spring.boot.resturantbackend.models.BaseEntity;
import com.spring.boot.resturantbackend.models.security.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(schema = "hr")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ContactInfo extends BaseEntity<String> {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private String status;
    @ManyToOne
    private Account account;
    @OneToMany(mappedBy = "contactInfo")
    private List<Comment> comments;
}
