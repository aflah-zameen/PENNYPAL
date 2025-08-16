package com.application.pennypal.application.dto.output.card;

import com.application.pennypal.domain.card.valueObject.CardType;

import java.math.BigDecimal;

public record PaymentMethodOutputModel(
        String id,
        String type,
        String name,
        String holder,
        BigDecimal balance,
        String cardNumber,
        boolean isActive
) {
}
