package com.application.pennypal.application.dto.input.goal;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EditGoalInputModel(String goalId,
                                 String title,
                                 String description,
                                 BigDecimal targetAmount,
                                 LocalDate startDate,
                                 LocalDate endDate,
                                 int priorityLevel,
                                 String categoryId) {
}
