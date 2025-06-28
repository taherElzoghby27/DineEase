package com.spring.boot.resturantbackend.models.security;

import com.spring.boot.resturantbackend.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "hr")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccountDetails extends BaseEntity<String> {
    @Column(nullable = false)
    private String age;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String address;
    @OneToOne
    private Account account;
}
