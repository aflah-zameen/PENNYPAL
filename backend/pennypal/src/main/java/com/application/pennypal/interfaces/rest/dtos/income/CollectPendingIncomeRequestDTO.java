package com.application.pennypal.interfaces.rest.dtos.income;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CollectPendingIncomeRequestDTO(@NotNull(message = "Income id cannot be null") Long incomeId,
                                             @NotNull(message = "Payment date cannot be null") LocalDate incomeDate) {
}
