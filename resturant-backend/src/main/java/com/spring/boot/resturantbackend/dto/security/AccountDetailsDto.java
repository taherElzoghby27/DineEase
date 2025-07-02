package com.spring.boot.resturantbackend.dto.security;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccountDetailsDto {
    private Long id;
    private String phoneNumber;
    @Min(value = 16, message = "error.age")
    @Max(value = 80, message = "error.age")
    private Long age;
    private String address;
    private Long accountId;
}
