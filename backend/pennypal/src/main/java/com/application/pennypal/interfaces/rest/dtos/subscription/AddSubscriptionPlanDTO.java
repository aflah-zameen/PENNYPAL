package com.application.pennypal.interfaces.rest.dtos.subscription;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.*;

public record AddSubscriptionPlanDTO(

        String id,
        @NotBlank(message = "Plan name is required")
        @Size(max = 100, message = "Plan name must be under 100 characters")
        String name,

        String description,

        List<String> features,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
        @Digits(integer = 10, fraction = 2, message = "Amount must be a valid monetary value")
        BigDecimal amount,

        @Min(value = 1, message = "Duration must be at least 1 day")
        @Max(value = 365, message = "Duration cannot exceed 365 days")
        int durationDays

) {}

