package com.application.pennypal.interfaces.rest.dtos.card;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CardTransactionResponseDTO(
        String id,
        String name,
        String category,
        LocalDate date,
        BigDecimal amount,
        String type
) {
}
