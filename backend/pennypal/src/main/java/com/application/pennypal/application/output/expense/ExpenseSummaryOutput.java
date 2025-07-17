package com.application.pennypal.application.output.expense;

public record ExpenseSummaryOutput(TotalExpenseSummaryOutput totalExpenseSummaryOutput,
                                   PendingExpenseSummaryOutput pendingExpenseSummaryOutput,
                                   ActiveRecurringExpenseOutput activeRecurringExpenseOutput) {
}
