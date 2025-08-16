package com.application.pennypal.application.dto.output.goal;

import java.math.BigDecimal;

public record AdminGoalStats(
        long totalGoals,
        long activeGoals,
        long completedGoals,
        BigDecimal totalContributions,
        long pendingWithdrawals

) {
}
