package com.application.pennypal.interfaces.rest.dtos.transaction;

import com.application.pennypal.domain.valueObject.PaymentMethod;
import com.application.pennypal.interfaces.rest.dtos.card.CardUserResponseDTO;
import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryUserResponseDTO;
import com.application.pennypal.interfaces.rest.dtos.user.UserResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionResponseDTO(String id,
                                     String user,
                                     CategoryUserResponseDTO category,
                                     CardUserResponseDTO card,
                                     BigDecimal amount,
                                     String transactionType,
                                     String title,
                                     String description,
                                     String paymentMethod,
                                     LocalDate transactionDate,
                                     boolean isFromRecurring,
                                     String recurringTransactionId,
                                     String transferToUserId,
                                     String transferFromUserId,
                                     LocalDateTime createdAt) {
}
