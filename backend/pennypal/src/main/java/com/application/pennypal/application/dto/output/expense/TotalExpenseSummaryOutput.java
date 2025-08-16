package com.application.pennypal.application.dto.output.expense;

import java.math.BigDecimal;

public record TotalExpenseSummaryOutput(BigDecimal totalExpenses, Double progressValue) {
}
