package com.spring.boot.backend.vm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategoryVm {
    private Long id;
    private String name;
    private String logo;
    private String flag;
}
