package com.application.pennypal.application.dto.output.expense;

import java.math.BigDecimal;
import java.util.List;

public record AllPendingExpenseSummaryOutput(List<PendingExpenseOutput> pendingExpenseOutputList,
                                             int totalCount,
                                             BigDecimal totalAmount) {
}
