package com.spring.boot.resturantbackend.dto.contact_info;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentDto {
    private Long id;
    @NotEmpty(message = "not_empty.value")
    private String value;
    @NotNull(message = "not_empty.order_number")
    private Long orderNumber;
    @NotEmpty(message = "not_empty.sender")
    private String sender;
    private ContactInfoDto contactInfo;
}
