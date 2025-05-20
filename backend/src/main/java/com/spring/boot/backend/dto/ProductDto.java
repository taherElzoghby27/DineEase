package com.spring.boot.backend.dto;

import com.spring.boot.backend.vm.CategoryVm;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductDto {
    private Long id;
    @NotEmpty(message = "not_empty.name")
    @Size(min = 7, max = 50, message = "size.name")
    private String name;
    @NotEmpty(message = "not_empty.image_path")
    private String imagePath;
    @NotEmpty(message = "not_empty.description")
    private String description;
    private Double price;
    private CategoryVm category;
}
