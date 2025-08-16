package com.application.pennypal.interfaces.rest.dtos.goal;

import com.application.pennypal.application.dto.output.user.UserOutputModel;
import com.application.pennypal.domain.valueObject.GoalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record GoalAdminResponseDTO(
        String id,
        UserOutputModel user,
        String goalName,
        BigDecimal targetAmount,
        BigDecimal contributedAmount,
        GoalStatus status,
        LocalDateTime createdDate,
        LocalDate targetDate,
        LocalDateTime lastContribution,
        String category,
        String description
) {
}
