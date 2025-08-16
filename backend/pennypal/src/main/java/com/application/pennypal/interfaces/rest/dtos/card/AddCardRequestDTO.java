package com.application.pennypal.interfaces.rest.dtos.card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record AddCardRequestDTO(
        @NotNull(message = "Card number cannot be null")
        @NotBlank(message = "Card number cannot be blank")
        @Pattern(regexp = "^(\\d{4}[\\s-]?){3}\\d{4}$",
                message = "Card number must be 16 digits with optional spaces or hyphens (e.g., 1234 5678 9012 3456 or 1234-5678-9012-3456)")
        String number,
        @NotNull(message = "Card name cannot be null")
        @NotBlank(message = "Card name cannot be blank")
        String name,
        @NotNull(message = "Holder name cannot be null")
        @NotBlank(message = "Holder name cannot be blank")
        String holder,
        @NotNull(message = "Expiry cannot be null")
        @NotBlank(message = "Expiry cannot be blank")
        @Pattern(regexp = "^(0[1-9]|1[0-2])\\/\\d{2}$", message = "Expiry must be in MM/YY format (e.g., 05/25 or 12/30)")
        String expiry,
        @NotNull(message = "Card type cannot be null")
        @NotBlank(message = "Card type cannot be blank")
        String type,
        @NotNull(message = "Card balance cannot be null")
        BigDecimal balance,
        @NotNull(message = "Card gradient cannot be null")
        @NotBlank(message = "Card gradient cannot be blank")
        String gradient,
        @NotNull(message = "Card pin cannot be null")
        @NotBlank(message = "Card pin cannot be blank")
        @Pattern(regexp = "\\d{4}", message = "Card pin must be exactly 4 digits")
        String pin,
        String notes
) {
}
