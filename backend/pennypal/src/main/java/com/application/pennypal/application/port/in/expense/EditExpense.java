package com.application.pennypal.application.port.in.expense;

import com.application.pennypal.application.dto.input.expense.ExpenseInputModel;

public interface EditExpense {
    void execute(Long userId,ExpenseInputModel expenseInputModel);
}
