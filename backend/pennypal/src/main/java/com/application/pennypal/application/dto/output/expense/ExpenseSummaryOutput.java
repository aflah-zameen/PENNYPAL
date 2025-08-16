package com.application.pennypal.application.dto.output.expense;

public record ExpenseSummaryOutput(TotalExpenseSummaryOutput totalExpenseSummaryOutput,
                                   PendingExpenseSummaryOutput pendingExpenseSummaryOutput,
                                   ActiveRecurringExpenseOutput activeRecurringExpenseOutput) {
}
