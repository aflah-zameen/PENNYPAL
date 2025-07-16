package com.application.pennypal.application.usecases.expense;

import com.application.pennypal.application.input.expense.ExpenseInputModel;

public interface EditExpense {
    void execute(Long userId,ExpenseInputModel expenseInputModel);
}
