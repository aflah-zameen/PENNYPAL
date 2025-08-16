package com.application.pennypal.application.dto.input.income;

import com.application.pennypal.domain.valueObject.RecurrenceFrequency;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeInputModel(String title, BigDecimal amount, Long categoryId, LocalDate incomeDate,
                               String description, Boolean isRecurring, LocalDate startDate, LocalDate endDate,
                               RecurrenceFrequency frequency, Boolean recurrenceActive) {
}
