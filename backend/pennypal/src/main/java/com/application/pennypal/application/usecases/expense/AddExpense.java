package com.application.pennypal.application.usecases.expense;

import com.application.pennypal.domain.user.entity.Expense;
import com.application.pennypal.domain.user.valueObject.ExpenseDTO;

public interface AddExpense {
    Expense add(ExpenseDTO expense,Long userId);
}
