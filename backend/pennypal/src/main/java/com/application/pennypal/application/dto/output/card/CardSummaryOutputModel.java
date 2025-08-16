package com.application.pennypal.application.dto.output.card;

import com.application.pennypal.domain.card.valueObject.CardType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CardSummaryOutputModel(String cardId,
                                     String userName,
                                     String cardName,
                                     String cardNumber,
                                     LocalDate expiry,
                                     CardType cardType,
                                     BigDecimal balanceAmount,
                                     BigDecimal income,
                                     BigDecimal expense,
                                     String gradient,
                                     boolean active,
                                     LocalDateTime createdAt) {
}
