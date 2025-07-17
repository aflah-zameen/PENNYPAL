package com.application.pennypal.application.output.expense;

import java.math.BigDecimal;

public record TotalExpenseSummaryOutput(BigDecimal totalAmount, Double progressValue) {
}
