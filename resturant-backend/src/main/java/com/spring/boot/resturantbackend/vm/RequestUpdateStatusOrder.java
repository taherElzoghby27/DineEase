package com.spring.boot.resturantbackend.vm;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RequestUpdateStatusOrder {
    private Long id;
    @NotEmpty(message = "status.required")
    private String status;
}
