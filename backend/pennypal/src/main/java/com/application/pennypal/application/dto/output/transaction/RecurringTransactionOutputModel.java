package com.application.pennypal.application.dto.output.transaction;

import com.application.pennypal.application.dto.output.card.CardOutputModel;
import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.domain.valueObject.RecurrenceFrequency;
import com.application.pennypal.domain.valueObject.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record RecurringTransactionOutputModel(String recurringId,
                                              String userId,
                                              CardOutputModel card,
                                              CategoryUserOutput category,
                                              TransactionType transactionType,
                                              String title,
                                              String description,
                                              BigDecimal amount,
                                              RecurrenceFrequency frequency,
                                              LocalDate startDate,
                                              LocalDate endDate,
                                              LocalDate lastGeneratedDate,
                                              boolean active,
                                              LocalDateTime createdAt) {
}
