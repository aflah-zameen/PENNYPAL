package com.application.pennypal.interfaces.rest.dtos.goal;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalContributionResponseDTO(Long id, BigDecimal amount, LocalDate date,String notes) {
}
