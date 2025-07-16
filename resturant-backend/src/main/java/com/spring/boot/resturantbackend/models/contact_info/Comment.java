package com.spring.boot.resturantbackend.models.contact_info;

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
public class Comment extends BaseEntity<String> {
    @Column(nullable = false)
    private String value;
    @Column(nullable = false)
    private Long order;
    @Column(nullable = false)
    private String sender;
    @ManyToOne
    private ContactInfo contactInfo;
}
