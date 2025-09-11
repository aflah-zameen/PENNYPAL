package com.application.pennypal.application.dto.output.coin;

import java.math.BigDecimal;

public record RedemptionStatsOutputModel(
        BigDecimal totalCoinsEarned,
        BigDecimal totalMoneyRedeemed,
        float pendingRequests
) {
}
