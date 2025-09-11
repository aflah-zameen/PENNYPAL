package com.application.pennypal.application.dto.output.lend;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LendingRequestOutputModel(
        String id,
        String senderId,
        String senderName,
        String recipientId,
        String recipientName,
        BigDecimal amount,
        String message,
        LocalDateTime repaymentDeadline,
        LocalDateTime acceptedDeadline,
        String status,
        LocalDateTime requestDate,
        LocalDateTime updatedAt
) {
}
