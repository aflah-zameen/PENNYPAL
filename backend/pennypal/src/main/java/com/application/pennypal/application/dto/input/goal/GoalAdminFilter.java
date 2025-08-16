package com.application.pennypal.application.dto.input.goal;

import com.application.pennypal.domain.valueObject.GoalStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GoalAdminFilter(
        String keyword,
        GoalStatus goalStatus,
        LocalDateTime dateFrom,
        LocalDateTime dateTo,
        String categoryId,
        BigDecimal minAmount,
        BigDecimal maxAmount,
        boolean asc
) {
}
