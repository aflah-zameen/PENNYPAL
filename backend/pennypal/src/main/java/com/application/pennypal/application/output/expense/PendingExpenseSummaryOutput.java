package com.application.pennypal.application.output.expense;

import java.math.BigDecimal;

public record PendingExpenseSummaryOutput(BigDecimal totalAmount, Long pendingExpenses) {
}
