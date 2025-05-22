package com.spring.boot.resturantbackend.models;
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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String imagePath;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Double price;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
