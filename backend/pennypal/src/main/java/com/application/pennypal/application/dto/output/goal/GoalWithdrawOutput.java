package com.application.pennypal.application.dto.output.goal;

import com.application.pennypal.application.dto.output.user.UserOutputModel;
import com.application.pennypal.domain.valueObject.GoalWithdrawRequestStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GoalWithdrawOutput(
        String id,
        String goalId,
        UserOutputModel user,
        BigDecimal amount,
        LocalDateTime requestDate,
        String status
) {
}
