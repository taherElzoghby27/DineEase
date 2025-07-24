package com.spring.boot.resturantbackend.models.contact_info;

import com.spring.boot.resturantbackend.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "hr", name = "comment_contact_info")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Comment extends BaseEntity<String> {
    @Column(nullable = false)
    private String value;
    @Column(nullable = false)
    private Long orderNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    private ContactInfo contactInfo;
}
