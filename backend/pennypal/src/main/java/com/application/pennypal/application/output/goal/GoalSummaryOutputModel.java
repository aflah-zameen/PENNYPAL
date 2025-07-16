package com.application.pennypal.application.output.goal;

import java.math.BigDecimal;

public record GoalSummaryOutputModel(Long totalActiveGoals, BigDecimal totalSaved, Long completedGoals) {
}
