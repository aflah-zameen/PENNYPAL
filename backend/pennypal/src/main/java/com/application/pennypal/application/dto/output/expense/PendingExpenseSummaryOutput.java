package com.application.pennypal.application.dto.output.expense;

import java.math.BigDecimal;

public record PendingExpenseSummaryOutput(BigDecimal totalAmount, Long pendingExpenses) {
}
