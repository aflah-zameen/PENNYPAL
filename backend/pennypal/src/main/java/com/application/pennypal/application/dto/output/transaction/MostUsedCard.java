package com.application.pennypal.application.dto.output.transaction;

import com.application.pennypal.domain.card.valueObject.CardType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MostUsedCard(
        String id,
        String name,
        String number,
        LocalDate expiry,
        CardType type,
        BigDecimal balance,
        String gradient,
        boolean active,
        BigDecimal amount
) {
}
