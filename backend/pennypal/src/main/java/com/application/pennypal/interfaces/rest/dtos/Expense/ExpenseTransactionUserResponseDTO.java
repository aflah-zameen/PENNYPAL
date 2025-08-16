package com.application.pennypal.interfaces.rest.dtos.Expense;

import com.application.pennypal.interfaces.rest.dtos.card.CardUserResponseDTO;
import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryUserResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseTransactionUserResponseDTO(String transactionId, CategoryUserResponseDTO category,
                                                CardUserResponseDTO card, BigDecimal amount, String title,
                                                String description, String paymentMethod, LocalDateTime expenseDate, boolean
                                                isRecurring, String transactionStatus) {
}
