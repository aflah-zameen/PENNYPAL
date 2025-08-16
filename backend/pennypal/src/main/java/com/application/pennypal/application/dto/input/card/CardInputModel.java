package com.application.pennypal.application.dto.input.card;

import com.application.pennypal.domain.card.valueObject.CardType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardInputModel(
        String name,
        String holder,
        String cardNumber,
        LocalDate expiry,
        CardType cardType,
        BigDecimal balanceAmount,
        String gradient,
        String pin
) {
}
