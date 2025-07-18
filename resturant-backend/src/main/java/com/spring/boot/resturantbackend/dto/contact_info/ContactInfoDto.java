package com.spring.boot.resturantbackend.dto.contact_info;

import com.spring.boot.resturantbackend.utils.enums.FilterContactInfo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "not_empty.status")
    @Enumerated(EnumType.STRING)
    private FilterContactInfo status;
    private List<CommentDto> comment;
}
