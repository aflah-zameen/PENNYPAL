package com.application.pennypal.application.dto.output.income;

import java.math.BigDecimal;

public record TotalIncomeSummaryOutput(BigDecimal totalIncomes, Double progressValue) {
}
