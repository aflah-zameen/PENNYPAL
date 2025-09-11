package com.application.pennypal.interfaces.rest.dtos.coin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AdminRedemptionResponse(
        String id,
        String userId,
        String userName,
        String userEmail,
        BigDecimal coinAmount,
        BigDecimal realMoneyAmount,
        String status,
        LocalDateTime requestedAt,
        LocalDateTime processedAt,
        String processedBy
) {
}
