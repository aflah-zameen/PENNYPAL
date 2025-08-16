package com.application.pennypal.application.dto.output.transaction;

import java.math.BigDecimal;

public record ExpenseDataChart(
        String category,
        BigDecimal amount,
        Double percentage
) {
}
