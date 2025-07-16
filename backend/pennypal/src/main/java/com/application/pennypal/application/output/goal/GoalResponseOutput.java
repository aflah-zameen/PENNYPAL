package com.application.pennypal.application.output.goal;

import com.application.pennypal.application.output.category.CategoryUserOutput;
import com.application.pennypal.domain.valueObject.GoalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record GoalResponseOutput(Long id, Long userId, String title, String description, BigDecimal targetAmount,
                                 BigDecimal currentAmount, LocalDate startDate, LocalDate endDate, GoalStatus status,
                                 CategoryUserOutput category, Integer priorityLevel, Boolean deleted,
                                 LocalDateTime createdAt, LocalDateTime updatedAt,
                                 List<GoalContributionOutput> contributions) {

}
