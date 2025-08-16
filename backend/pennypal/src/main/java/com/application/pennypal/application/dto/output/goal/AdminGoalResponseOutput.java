package com.application.pennypal.application.dto.output.goal;

import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.application.dto.output.user.UserOutputModel;
import com.application.pennypal.domain.valueObject.GoalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AdminGoalResponseOutput(
        String goalId, UserOutputModel user, String title, String description, BigDecimal targetAmount,
        BigDecimal currentAmount, LocalDate startDate, LocalDate endDate,LocalDateTime lastContributed, GoalStatus status,
        String category, LocalDateTime createdAt, LocalDateTime updatedAt
) {
}
