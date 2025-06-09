package com.spring.boot.resturantbackend.setting;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "token")
public class JWTToken {
    private String secret;
    private String time;
}
