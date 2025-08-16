package com.application.pennypal.application.dto.output.goal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record GoalContributionOutput(String contributionId, String cardNumber, BigDecimal amount, LocalDateTime date, String notes) {
}
