package com.application.pennypal.interfaces.rest.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record IncomeRequest(@NotNull(message = "Amount can't be null") BigDecimal amount, String source, @NotNull(message = "Date can't br null") String income_date, String notes) {
}
