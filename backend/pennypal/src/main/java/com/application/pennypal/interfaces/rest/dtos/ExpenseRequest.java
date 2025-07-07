package com.application.pennypal.interfaces.rest.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ExpenseRequest(@NotNull(message = "Expense name should be given") String name,
                             @NotNull(message = "Expense amount should be given") BigDecimal amount,
                             @NotNull(message = "Expense category should be given") Long category,
                             @NotNull(message = "Expense type should be given") String type,
                             @NotNull(message = "Expense start date should be given") String startDate,
                             @NotNull(message = "Expense end date should be given") String endDate)  {
}
