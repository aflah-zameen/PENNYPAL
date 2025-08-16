package com.application.pennypal.application.dto.output.transaction;

import com.application.pennypal.application.dto.output.card.CardOutputModel;
import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.domain.valueObject.RecurringLogStatus;
import com.application.pennypal.domain.valueObject.TransactionType;


import java.math.BigDecimal;
import java.time.LocalDate;

public record PendingTransactionOutput(String transactionId,
                                       String title,
                                       BigDecimal amount,
                                       CategoryUserOutput category,
                                       CardOutputModel card,
                                       TransactionType transactionType,
                                       String description,
                                       LocalDate transactionDate,
                                       RecurringLogStatus status) {
}
