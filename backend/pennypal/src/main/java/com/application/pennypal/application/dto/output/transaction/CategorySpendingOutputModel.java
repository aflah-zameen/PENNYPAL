package com.application.pennypal.application.dto.output.transaction;

import com.application.pennypal.application.dto.output.category.CategoryUserOutput;

import java.math.BigDecimal;

public record CategorySpendingOutputModel(
        CategoryUserOutput category,
        BigDecimal amount,
        Long transactionCount
) {
}
