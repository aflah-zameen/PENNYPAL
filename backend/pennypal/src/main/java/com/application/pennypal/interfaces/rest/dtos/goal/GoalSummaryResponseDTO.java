package com.application.pennypal.interfaces.rest.dtos.goal;

import java.math.BigDecimal;

public record GoalSummaryResponseDTO(Long totalActiveGoals, BigDecimal totalSaved,Long completedGoals) {
}
