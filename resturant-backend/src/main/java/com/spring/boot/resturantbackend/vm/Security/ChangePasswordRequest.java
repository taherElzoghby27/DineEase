package com.spring.boot.resturantbackend.vm.Security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
        name = "ChangePasswordRequest",
        description = "Request model for changing user password in the restaurant system. Requires current password verification and new password meeting security requirements"
)
public class ChangePasswordRequest {
    @Schema(
            description = "Username of the account whose password is being changed. Must match the authenticated user's username",
            example = "restaurant_manager",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 7
    )
    @NotEmpty(message = "not_empty.username")
    @Size(min = 7, message = "size.username")
    private String username;
    @Schema(
            description = "Current password for verification purposes. Must meet security requirements: " +
                          "minimum 7 characters, at least one lowercase letter, one uppercase letter, " +
                          "one digit, and one special character",
            example = "CurrentPass123!",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 7,
            pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{7,}$"
    )
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{7,}$", message = "error.password")
    private String oldPassword;
    @Schema(
            description = "New password to replace the current one. Must meet security requirements: " +
                          "minimum 7 characters, at least one lowercase letter, one uppercase letter, " +
                          "one digit, and one special character. Should be different from the current password",
            example = "NewSecurePass456@",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 7,
            pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{7,}$"
    )
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{7,}$", message = "error.password")
    private String newPassword;
}
