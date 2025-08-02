package com.spring.boot.resturantbackend.dto.contact_info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.boot.resturantbackend.vm.ContactInfoVm;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto implements Serializable {
    private Long id;
    @NotEmpty(message = "not_empty.value")
    private String value;
    private String createdBy;
    private Long orderNumber;
    private LocalDateTime createdDate;
    private Long receiverId;
    private Long contactInfoId;
    private ContactInfoVm contactInfo;
}
