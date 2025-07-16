package com.application.pennypal.application.usecases.expense;

public interface DeleteExpense {
    void execute(Long userId,Long expenseId);
}
