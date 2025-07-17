package com.application.pennypal.application.output.expense;

import com.application.pennypal.application.output.income.PendingIncomeOutput;

import java.math.BigDecimal;
import java.util.List;

public record AllPendingExpenseSummaryOutput(List<PendingExpenseOutput> pendingExpenseOutputList,
                                             int totalCount,
                                             BigDecimal totalAmount) {
}
