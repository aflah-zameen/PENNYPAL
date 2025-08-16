package com.application.pennypal.interfaces.rest.dtos.goal;

import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryUserResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record GoalResponseDTO(String id,String userId, String title, String description, BigDecimal targetAmount,
                              BigDecimal currentAmount, LocalDate startDate, LocalDate endDate, String status,
                              CategoryUserResponseDTO category, Integer priorityLevel, Boolean deleted,
                              LocalDateTime createdAt, LocalDateTime updatedAt,
                              List<GoalContributionResponseDTO> contributions) {
}
