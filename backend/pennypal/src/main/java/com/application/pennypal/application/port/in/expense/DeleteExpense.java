package com.application.pennypal.application.port.in.expense;

public interface DeleteExpense {
    void execute(Long userId,Long expenseId);
}
