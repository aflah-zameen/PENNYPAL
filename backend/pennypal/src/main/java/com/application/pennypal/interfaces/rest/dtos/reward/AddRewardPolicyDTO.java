package com.application.pennypal.interfaces.rest.dtos.reward;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AddRewardPolicyDTO(
        @NotNull(message = "Coin amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Coin amount must be greater than zero")
        BigDecimal coinAmount,

        @NotBlank(message = "Action type is required")
        @Size(max = 100, message = "Action type must not exceed 100 characters")
        String actionType,

        @Size(max = 255, message = "Description must not exceed 255 characters")
        String description,

        boolean isActive,

        String id
) {
}
