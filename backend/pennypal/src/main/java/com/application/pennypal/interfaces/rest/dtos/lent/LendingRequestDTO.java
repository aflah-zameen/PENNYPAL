package com.application.pennypal.interfaces.rest.dtos.lend;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;

public record LendingRequestDTO(
        @NotNull(message = "The recipient of the request cannot be null")
        @NotBlank(message = "The recipient of the request cannot be blank")
        String requestedTo,

        @NotNull(message = "The requested amount cannot be null")
        @Positive(message = "The amount must be a positive number")
        BigDecimal amount,

        @Size(max = 500, message = "The message must not exceed 500 characters")
        String message,

        @NotNull(message = "The proposed deadline cannot be null")
        @FutureOrPresent(message = "The proposed deadline must be a future or present date")
        LocalDateTime proposedDeadline
) {
}