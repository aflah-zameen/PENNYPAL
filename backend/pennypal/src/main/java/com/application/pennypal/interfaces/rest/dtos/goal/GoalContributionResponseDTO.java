package com.application.pennypal.interfaces.rest.dtos.goal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record GoalContributionResponseDTO(String contributionId, BigDecimal amount, LocalDateTime date, String notes,BigDecimal coins) {
}
