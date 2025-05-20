package com.spring.boot.backend.vm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductVm {
    private Long id;
    private String name;
    private String imagePath;
    private String description;
    private Double price;
}
