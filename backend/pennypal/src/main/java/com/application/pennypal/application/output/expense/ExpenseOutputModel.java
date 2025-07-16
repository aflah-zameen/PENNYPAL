package com.application.pennypal.application.output.expense;

import com.application.pennypal.application.output.category.CategoryUserOutput;
import com.application.pennypal.domain.valueObject.RecurrenceFrequency;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseOutputModel(Long id, String name, CategoryUserOutput category, BigDecimal amount, LocalDate startDate,
                                 LocalDate endDate, RecurrenceFrequency type, Boolean deleted ) {}
