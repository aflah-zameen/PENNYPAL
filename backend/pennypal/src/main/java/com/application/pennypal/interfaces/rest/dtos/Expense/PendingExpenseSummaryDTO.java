package com.application.pennypal.interfaces.rest.dtos.Expense;

import java.math.BigDecimal;

public record PendingExpenseSummaryDTO(BigDecimal totalAmount, Long pendingExpenses) {
}
