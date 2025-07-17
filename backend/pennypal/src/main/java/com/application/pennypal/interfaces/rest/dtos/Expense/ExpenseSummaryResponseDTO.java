package com.application.pennypal.interfaces.rest.dtos.Expense;

public record ExpenseSummaryResponseDTO(TotalExpenseSummaryDTO totalExpenseSummary,
                                        PendingExpenseSummaryDTO pendingExpenseSummary,
                                        ActiveRecurringExpenseDTO activeRecurringExpense) {
}
