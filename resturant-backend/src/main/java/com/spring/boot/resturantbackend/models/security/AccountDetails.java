package com.spring.boot.resturantbackend.models.security;

import com.spring.boot.resturantbackend.models.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Min(value = 16, message = "error.age")
    @Max(value = 80, message = "error.age")
    private Long age;
    private String phoneNumber;
    private String address;
    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
