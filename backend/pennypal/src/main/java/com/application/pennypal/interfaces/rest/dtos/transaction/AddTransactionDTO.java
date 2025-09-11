package com.application.pennypal.interfaces.rest.dtos.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddTransactionDTO(@NotNull(message = "Amount can't be null")
                                BigDecimal amount,
                                @NotNull(message = "Tile cannot be null")
                                String title,
                                @NotNull(message = "Payment method cannot be null")
                                @NotBlank(message = "Payment method cannot be blank")
                                String paymentMethod,
                                @NotNull(message = "Transaction type should be defined")
                                String transactionType,
                                String cardId,
                                @NotNull(message = "Category should be selected") String categoryId,
                                @NotNull(message = "Transaction date cannot be null") String transactionDate,
                                String description,
                                String transferToUserId,
                                String transferFromUserId) {
}
