package com.application.pennypal.interfaces.rest.dtos.goal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record EditGoalRequestDTO(@NotNull(message = "Goal Id cannot be null") Long goalId,
                                 @NotBlank(message = "Title cannot be null") String title,
                                 String description,
                                 @NotNull(message = "Target amount cannot be null") BigDecimal targetAmount,
                                 @NotBlank(message = "Start date cannot be null") String startDate,
                                 @NotBlank(message = "End date cannot be null") String endDate,
                                 Integer priorityLevel,
                                 @NotNull(message = "Category cannot be null")Long categoryId) {

}
