package com.application.pennypal.application.dto.output.payment;

import java.math.BigDecimal;

public record PaymentResultOutputModel(
        boolean success,
        String paymentId,
        BigDecimal price
) {
}
