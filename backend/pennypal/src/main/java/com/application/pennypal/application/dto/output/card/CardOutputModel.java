package com.application.pennypal.application.dto.output.card;

import com.application.pennypal.domain.card.valueObject.CardType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardOutputModel(String cardId,
                              String cardName,
                              String cardNumber,
                              LocalDate expiry,
                              CardType cardType,
                              BigDecimal balanceAmount,
                              String gradient,
                              boolean active
) {
}
