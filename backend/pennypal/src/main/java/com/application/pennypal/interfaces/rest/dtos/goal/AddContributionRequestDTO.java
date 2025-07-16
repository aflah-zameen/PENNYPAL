package com.application.pennypal.interfaces.rest.dtos.goal;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddContributionRequestDTO(@NotNull(message = "Goal ID cannot be null") Long goalId,
                                        @NotNull(message = "Amount cannot be null") BigDecimal amount,
                                        String notes) {
}
