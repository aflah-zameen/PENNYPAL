package com.application.pennypal.application.output.income;

import java.math.BigDecimal;

public record TotalIncomeSummaryOutput(BigDecimal totalIncome, Double progressValue) {
}
