package com.application.pennypal.application.dto.output.transaction;

import com.application.pennypal.application.dto.output.card.CardOutputModel;
import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.domain.valueObject.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionOutputModel(String transactionId, String userId, CategoryUserOutput category, CardOutputModel cardId, BigDecimal amount,
                                     TransactionType transactionType, String title, String description,
                                     PaymentMethod paymentMethod, LocalDate transactionDate, Boolean isFromRecurring, String recurringTransactionId, String transferFromUserId, String transferToUserId,
                                     TransactionStatus transactionStatus, LocalDateTime createdAt) {
}
