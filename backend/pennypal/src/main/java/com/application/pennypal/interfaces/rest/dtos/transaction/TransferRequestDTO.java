package com.application.pennypal.interfaces.rest.dtos.transaction;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TransferRequestDTO(

        @NotBlank(message = "Recipient ID is required")
        String recipientId,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be at least 0.01")
        @Digits(integer = 12, fraction = 2, message = "Invalid amount format")
        BigDecimal amount,

        @Size(max = 255, message = "Note can't exceed 255 characters")
        String note,

        @NotBlank(message = "PIN is required")
        @Pattern(regexp = "^\\d{4,6}$", message = "PIN must be 4 to 6 digits")
        String pin,

        @NotBlank(message = "Payment method ID is required")
        String paymentMethodId

) {}
