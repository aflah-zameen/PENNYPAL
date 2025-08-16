package com.application.pennypal.interfaces.rest.dtos.transaction;

import com.application.pennypal.interfaces.rest.dtos.card.CardUserResponseDTO;
import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryUserResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PendingTransactionResponseDTO (String transactionId,
                                             String title,
                                             BigDecimal amount,
                                             CategoryUserResponseDTO category,
                                             CardUserResponseDTO card,
                                             String transactionType,
                                             String description,
                                             LocalDate transactionDate,
                                             String status){}