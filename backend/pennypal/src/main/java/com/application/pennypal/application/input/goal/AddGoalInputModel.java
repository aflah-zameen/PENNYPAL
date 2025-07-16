package com.application.pennypal.application.input.goal;



import java.math.BigDecimal;
import java.time.LocalDate;

public record AddGoalInputModel(String title,
                                String description,
                                BigDecimal targetAmount,
                                LocalDate startDate,
                                LocalDate endDate,
                                int priorityLevel,
                                Long categoryId
) {
}

