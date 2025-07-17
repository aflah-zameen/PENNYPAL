package com.application.pennypal.interfaces.rest.dtos.Expense;

import java.math.BigDecimal;

public record TotalExpenseSummaryDTO(BigDecimal totalAmount, Double progressValue) {
}
