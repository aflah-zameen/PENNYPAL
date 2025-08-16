package com.application.pennypal.application.dto.input.goal;

import java.math.BigDecimal;

public record GoalWithdrawRequestInputModel(
        String userId,
        String goalId) {
}
