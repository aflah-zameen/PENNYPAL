package com.application.pennypal.application.input.expense;

import com.application.pennypal.domain.valueObject.RecurrenceFrequency;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseInputModel(Long id, String name, Long categoryId, BigDecimal amount, LocalDate startDate,
                                LocalDate endDate, RecurrenceFrequency type) {
}
