package com.spring.boot.resturantbackend.dto.contact_info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.boot.resturantbackend.vm.ContactInfoVm;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(
        name = "Comment Dto",
        description = "comment dto contains (id,value,orderNumber,createdBy,createdDate,receiverId,contactInfoId)"
)
public class CommentDto implements Serializable {
    private Long id;
    @NotEmpty(message = "not_empty.value")
    @Schema(
            name = "value",
            description = "message",
            example = "welcome ...."
    )
    private String value;
    private String createdBy;
    @Schema(
            name = "orderNumber",
            description = "number",
            example = "1 or 2 or 3 or 4 or ...."
    )
    private Long orderNumber;
    private LocalDateTime createdDate;
    @Schema(
            name = "receiverId",
            description = "receiver id (who receive this message)"
    )
    private Long receiverId;
    @Schema(
            name = "contactInfoId",
            description = "contact info id"
    )
    private Long contactInfoId;
    private ContactInfoVm contactInfo;
}
