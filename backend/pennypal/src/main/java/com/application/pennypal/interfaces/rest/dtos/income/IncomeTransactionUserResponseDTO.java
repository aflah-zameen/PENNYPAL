package com.application.pennypal.interfaces.rest.dtos.income;

import com.application.pennypal.interfaces.rest.dtos.card.CardUserResponseDTO;
import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryUserResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IncomeTransactionUserResponseDTO(String transactionId, CategoryUserResponseDTO category,
                                               CardUserResponseDTO card, BigDecimal amount, String title,
                                               String description, String paymentMethod, LocalDateTime incomeDate, boolean
                                               isRecurring,String transactionStatus
                                               ) { }
