package com.application.pennypal.application.dto.input.expense;

import com.application.pennypal.domain.valueObject.RecurrenceFrequency;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseInputModel(String title, BigDecimal amount, Long categoryId, LocalDate expenseDate,
                                String description, Boolean isRecurring, LocalDate startDate, LocalDate endDate,
                                RecurrenceFrequency frequency, Boolean recurrenceActive) {
}
