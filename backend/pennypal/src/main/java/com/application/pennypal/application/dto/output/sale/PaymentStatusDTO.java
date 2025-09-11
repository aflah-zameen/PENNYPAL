package com.application.pennypal.application.dto.output.sale;

import com.application.pennypal.domain.valueObject.TransactionStatus;

import java.math.BigDecimal;

public record PaymentStatusDTO(
        TransactionStatus status,
        Long count,
        BigDecimal totalAmount
) {
}
