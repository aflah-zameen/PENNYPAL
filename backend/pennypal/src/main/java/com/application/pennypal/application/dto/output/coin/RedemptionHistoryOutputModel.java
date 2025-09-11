package com.application.pennypal.application.dto.output.coin;

import com.application.pennypal.domain.coin.RedemptionRequestStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RedemptionHistoryOutputModel(
        String id,
        BigDecimal coinAmount,
        BigDecimal realMoneyAmount,
        RedemptionRequestStatus status,
        LocalDateTime requestedAt,
        LocalDateTime completedAt
) {
}
