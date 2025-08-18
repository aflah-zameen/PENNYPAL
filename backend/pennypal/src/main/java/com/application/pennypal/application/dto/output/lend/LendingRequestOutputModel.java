package com.application.pennypal.application.dto.output.lend;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LendingRequestOutputModel(
        String requestId,
        String requestedBy,
        String requestedTo,
        BigDecimal amount,
        String message,
        LocalDateTime proposedDeadline,
        LocalDateTime acceptedDeadline,
        String status,
        LocalDateTime requestedDate,
        LocalDateTime lastModified
) {
}
