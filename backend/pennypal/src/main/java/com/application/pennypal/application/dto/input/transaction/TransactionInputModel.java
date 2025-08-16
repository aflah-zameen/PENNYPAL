package com.application.pennypal.application.dto.input.transaction;

import com.application.pennypal.domain.valueObject.PaymentMethod;
import com.application.pennypal.domain.valueObject.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionInputModel(
        BigDecimal amount,
        String title,
        TransactionType transactionType,
        String cardId,
        String categoryId,
        LocalDate transactionDate,
        String description,
        PaymentMethod paymentMethod,
        String transferToUserId,
        String transferFromUserId
) {
}
