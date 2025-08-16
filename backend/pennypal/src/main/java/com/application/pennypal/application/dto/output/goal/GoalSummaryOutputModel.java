package com.application.pennypal.application.dto.output.goal;

import java.math.BigDecimal;

public record GoalSummaryOutputModel(Long totalActiveGoals, BigDecimal totalSaved, Long completedGoals) {
}
