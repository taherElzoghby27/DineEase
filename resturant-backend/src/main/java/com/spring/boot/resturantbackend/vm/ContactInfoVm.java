package com.spring.boot.resturantbackend.vm;

import com.spring.boot.resturantbackend.utils.enums.FilterContactInfo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ContactInfoVm implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String subject;
    private String message;
    @Enumerated(EnumType.STRING)
    private FilterContactInfo status;
}
