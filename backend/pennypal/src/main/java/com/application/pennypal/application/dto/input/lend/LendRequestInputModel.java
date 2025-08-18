package com.application.pennypal.application.dto.input.lend;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LendRequestInputModel(
        String requestTo,
        BigDecimal amount,
        String message,
        LocalDateTime proposedDeadline
) {
}
