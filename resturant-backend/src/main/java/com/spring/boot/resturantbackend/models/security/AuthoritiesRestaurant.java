package com.spring.boot.resturantbackend.models.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "authorities_restaurant",
        schema = "hr",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "authority"})
)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthoritiesRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String authority;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}

