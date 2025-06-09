package com.spring.boot.resturantbackend.vm.Security;

import com.spring.boot.resturantbackend.dto.security.UserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserAuthResponseVm {
    private Long id;
    private String username;
    private UserDetailsDto userDetailsDto;
    private String token;
}
