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
        name = "AccountAuthRequestVm",
        description = "Request model for user authentication containing login credentials for restaurant system access"
)
public class AccountAuthRequestVm {
    @Schema(
            description = "Account identifier (optional for login requests, typically used for account updates)",
            example = "12345",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Long id;
    @NotEmpty(message = "not_empty.username")
    @Size(min = 7, message = "size.username")
    @Schema(
            description = "Username for authentication. Must be at least 7 characters long and unique in the system",
            example = "restaurant_admin",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 7
    )
    private String username;
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{7,}$",
            message = "error.password"
    )
    @Schema(
            description = "Password for authentication. Must meet security requirements: " +
                          "minimum 7 characters, at least one lowercase letter, one uppercase letter, " +
                          "one digit, and one special character",
            example = "SecurePass123!",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 7,
            pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{7,}$"
    )
    private String password;
}
