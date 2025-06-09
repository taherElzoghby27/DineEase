package com.spring.boot.resturantbackend.vm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDtoVm {
    private Long id;
    private String role;

    public RoleDtoVm(String role) {
        this.role = role;
    }
}