package com.application.pennypal.application.dto.output.reward;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RewardPolicyOutputModel(
        String id,
        String actionType,
        BigDecimal coinAmount,
        LocalDateTime createdAt,
        boolean isActive,
        boolean isDeleted
) {
}
