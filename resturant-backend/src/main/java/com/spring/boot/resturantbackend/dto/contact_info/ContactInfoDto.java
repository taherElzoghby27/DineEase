package com.spring.boot.resturantbackend.dto.contact_info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.boot.resturantbackend.dto.security.AccountDto;
import com.spring.boot.resturantbackend.models.security.Account;
import com.spring.boot.resturantbackend.utils.enums.FilterContactInfo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactInfoDto {
    private Long id;
    @NotEmpty(message = "not_empty.name")
    @Size(min = 7, max = 100, message = "size.name")
    private String name;
    @Email(message = "not_valid.email")
    @NotEmpty(message = "not_empty.email")
    private String email;
    @NotEmpty(message = "not_empty.subject")
    private String subject;
    @NotEmpty(message = "not_empty.message")
    private String message;
    @Enumerated(EnumType.STRING)
    private FilterContactInfo status;
    private List<CommentDto> comment;
    private Long accountId;
}
