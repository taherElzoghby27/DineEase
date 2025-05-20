package com.spring.boot.backend.dto;

import com.spring.boot.backend.vm.ProductVm;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {
    private Long id;
    @NotEmpty(message = "not_empty.name")
    @Size(min = 7, max = 50, message = "size.name")
    private String name;
    @NotEmpty(message = "not_empty.logo")
    private String logo;
    @NotEmpty(message = "not_empty.flag")
    private String flag;
    private List<ProductVm> products;
}
