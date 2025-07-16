package com.application.pennypal.application.output.goal;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalContributionOutput(Long id, BigDecimal amount, LocalDate date, String notes) {
}
