package com.application.pennypal.application.dto.output.user;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransferTransaction(
        String id,
        String senderId,
        String recipientId,
        BigDecimal amount,
        String note,
        LocalDate date,
        String status,
        String failureReason,
        String type /// sent or received
) {
}
