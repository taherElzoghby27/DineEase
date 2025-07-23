package com.spring.boot.resturantbackend.models.contact_info;

import com.spring.boot.resturantbackend.models.BaseEntity;
import com.spring.boot.resturantbackend.models.security.Account;
import com.spring.boot.resturantbackend.utils.enums.FilterContactInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "not_empty.name")
    @Size(min = 7, max = 100, message = "size.name")
    private String name;
    @Column(nullable = false)
    @Email(message = "not_valid.email")
    @NotBlank(message = "not_empty.email")
    private String email;
    @Column(nullable = false)
    @NotBlank(message = "not_empty.subject")
    private String subject;
    @Column(nullable = false)
    @NotBlank(message = "not_empty.message")
    private String message;
    @Column(nullable = false)
    @NotNull(message = "not_empty.status")
    @Enumerated(EnumType.STRING)
    private FilterContactInfo status;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account senderAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account receivedAccount;
    @OneToMany(mappedBy = "contactInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;
}
