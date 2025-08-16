package com.application.pennypal.interfaces.rest.dtos.card;
import java.math.BigDecimal;

public record CardUserResponseDTO(
        String id,
        String name,
        String lastFour,
        String expiry,
        String type,
        BigDecimal balance,
        String gradient,
        boolean active
) {
}
