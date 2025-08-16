package com.application.pennypal.application.dto.output.expense;

import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.domain.valueObject.RecurrenceFrequency;
import com.application.pennypal.domain.valueObject.RecurringStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExpenseOutputModel(Long id, String title, BigDecimal amount, CategoryUserOutput category, LocalDate expenseDate,
                                 RecurringStatus status, String description, Boolean isRecurring, RecurrenceFrequency frequency,
                                 LocalDate startDate, LocalDate endDate, LocalDateTime updatedAt, LocalDateTime createdAt, Boolean recurrenceActive, Boolean deleted)  {}
