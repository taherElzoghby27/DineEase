package com.spring.boot.resturantbackend.vm.Security;

import com.spring.boot.resturantbackend.dto.security.AccountDetailsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(
        name = "UpdateProfileVm",
        description = "Request model for updating user profile information including username and detailed account information for restaurant system users"
)
public class UpdateProfileVm {
    @Schema(
            description = "Unique identifier of the account being updated. Required to identify which user profile to modify",
            example = "12345",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long id;
    @NotEmpty(message = "not_empty.username")
    @Size(min = 7, message = "size.username")
    @Schema(
            description = "New username for the account. Must be at least 7 characters long and unique across the system. " +
                          "This will be used for future logins",
            example = "updated_username",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 7
    )
    private String username;
    @Valid
    @Schema(
            description = "Detailed account information including personal details, contact information, " +
                          "and other profile-specific data that will be updated",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private AccountDetailsDto accountDetails;
}
