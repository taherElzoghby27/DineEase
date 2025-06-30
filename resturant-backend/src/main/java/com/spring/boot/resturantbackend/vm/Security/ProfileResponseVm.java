package com.spring.boot.resturantbackend.vm.Security;

import com.spring.boot.resturantbackend.dto.security.AccountDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProfileResponseVm {
    private Long id;
    private String username;
    private AccountDetailsDto accountDetails;
}
