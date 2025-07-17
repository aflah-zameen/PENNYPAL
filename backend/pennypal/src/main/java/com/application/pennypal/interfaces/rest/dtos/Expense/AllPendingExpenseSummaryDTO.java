package com.application.pennypal.interfaces.rest.dtos.Expense;

import java.math.BigDecimal;
import java.util.List;

public record AllPendingExpenseSummaryDTO(List<PendingExpenseResponseDTO> pendingExpenseList, int totalCount, BigDecimal totalAmount) {
}
