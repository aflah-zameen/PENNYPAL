package com.application.pennypal.application.dto.output.card;

import com.application.pennypal.application.dto.output.category.CategoryUserOutput;

import java.math.BigDecimal;

public record CardExpenseOverviewOutputModel(
        CategoryUserOutput category,
        BigDecimal amount,
        Double trend
) {
}
