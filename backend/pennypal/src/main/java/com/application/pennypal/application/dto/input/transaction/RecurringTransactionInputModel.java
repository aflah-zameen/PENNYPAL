package com.application.pennypal.application.dto.input.transaction;

import com.application.pennypal.domain.valueObject.RecurrenceFrequency;
import com.application.pennypal.domain.valueObject.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RecurringTransactionInputModel(String cardId,
                                             String categoryId,
                                             TransactionType transactionType,
                                             String title,
                                             String description,
                                             BigDecimal amount,
                                             RecurrenceFrequency frequency,
                                             LocalDate startDate,
                                             LocalDate endDate) {
}
