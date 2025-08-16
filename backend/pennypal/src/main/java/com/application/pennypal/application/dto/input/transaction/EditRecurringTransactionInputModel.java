package com.application.pennypal.application.dto.input.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EditRecurringTransactionInputModel(BigDecimal amount,
                                                 String title,
                                                 String cardId,
                                                 LocalDate endDate,
                                                 String description) {
}
