package com.application.pennypal.interfaces.rest.dtos.Expense;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddExpenseRequest(@NotNull(message = "Amount can't be null") BigDecimal amount,
                                String title,
                                Long categoryId,
                                String expenseDate,
                                String description,
                                @NotNull(message = "Recurrence value cannot be null") boolean isRecurring,
                                String frequency,
                                String startDate,
                                String endDate) { }
