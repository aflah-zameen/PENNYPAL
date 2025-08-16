package com.application.pennypal.interfaces.rest.dtos.transaction;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record EditRecurringTransactionDTO(@NotNull(message = "Amount can't be null") BigDecimal amount,
                                          @NotNull(message = "Tile cannot be null") String title,
                                          String cardId,
                                          @NotNull(message = "End date cannot be null")String endDate,
                                          String description) {
}
