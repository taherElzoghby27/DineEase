package com.spring.boot.resturantbackend.models.security;

import com.spring.boot.resturantbackend.models.ContactInfo;
import com.spring.boot.resturantbackend.models.Order;
import com.spring.boot.resturantbackend.models.UserDetails;
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
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private int age;
    @OneToOne(mappedBy = "userEntity")
    private UserDetails userDetails;
    @ManyToMany(mappedBy = "users")
    private List<RoleEntity> roles;
    @OneToMany(mappedBy = "user")
    private List<ContactInfo> contacts;
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<AuthoritiesRestaurant> authorities;
    @Column(nullable = false, length = 1)
    private String enabled;
}
