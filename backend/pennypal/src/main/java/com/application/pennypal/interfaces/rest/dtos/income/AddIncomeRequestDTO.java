package com.application.pennypal.interfaces.rest.dtos.income;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddIncomeRequestDTO(@NotNull(message = "Amount can't be null")
                            BigDecimal amount,
                                  String title,
                                  Long categoryId,
                                  String incomeDate,
                                  String description,
                                  @NotNull(message = "Recurrence value cannot be null")
                                boolean isRecurring,
                                  String frequency,
                                  String startDate,
                                  String endDate) {
}
