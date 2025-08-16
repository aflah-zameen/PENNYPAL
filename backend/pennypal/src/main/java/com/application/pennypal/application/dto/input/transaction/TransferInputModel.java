package com.application.pennypal.application.dto.input.transaction;

import java.math.BigDecimal;

public record TransferInputModel(
        String senderId,
        String recipientId,
        String methodId,
        String methodType,
        BigDecimal amount,
        String notes,
        String pin
) {
}
