package com.spring.boot.resturantbackend.models.security;

import com.spring.boot.resturantbackend.models.BaseEntity;
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
public class Role extends BaseEntity<String> {
    @Column(nullable = false)
    private String role;
    @ManyToMany(mappedBy = "roles")
    private List<Account> accounts;
}
