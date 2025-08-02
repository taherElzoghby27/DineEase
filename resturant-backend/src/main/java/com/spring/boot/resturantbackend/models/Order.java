package com.spring.boot.resturantbackend.models;

import com.spring.boot.resturantbackend.models.product.Product;
import com.spring.boot.resturantbackend.models.security.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(schema = "hr", name = "Order_table")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Order extends BaseEntity<String> {
    @Column(unique = true)
    private String code;
    private String status;
    @Column(nullable = false)
    private double totalPrice;
    @Column(nullable = false)
    private Long totalNumber;
    @ManyToMany
    @JoinTable(
            schema = "hr",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    List<Product> products;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Account account;
}
