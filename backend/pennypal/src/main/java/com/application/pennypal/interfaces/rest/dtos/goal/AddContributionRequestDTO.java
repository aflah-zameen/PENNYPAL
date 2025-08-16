package com.application.pennypal.interfaces.rest.dtos.goal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddContributionRequestDTO(@NotNull(message = "Goal ID cannot be null")
                                        String goalId,
                                        @NotNull(message = "Amount cannot be null")
                                        BigDecimal amount,
                                        @NotNull(message = "Card details cannot be null")
                                        @NotBlank(message = "Card details cannot be blank")
                                        String cardId,
                                        String notes) {
}
