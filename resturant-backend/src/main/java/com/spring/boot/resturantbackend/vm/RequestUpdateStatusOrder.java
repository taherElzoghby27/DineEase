package com.spring.boot.resturantbackend.vm;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(
        name = "RequestUpdateStatusOrder",
        description = "Request model for updating the status of an existing order"
)
public class RequestUpdateStatusOrder {
    @Schema(
            description = "Unique identifier of the order to be updated",
            example = "12345",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long id;
    @NotEmpty(message = "status.required")
    @Schema(
            description = "New status to be assigned to the order. Common values include: PENDING, CONFIRMED, IN_PREPARATION, READY, DELIVERED, CANCELLED",
            example = "CONFIRMED",
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"Pending", "Preparing", "Ready", "Delivered"}
    )
    private String status;
}
