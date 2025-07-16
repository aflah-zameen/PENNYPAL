package com.application.pennypal.application.usecases.expense;

import com.application.pennypal.domain.entity.Expense;
import com.application.pennypal.domain.valueObject.ExpenseDTO;

public interface AddExpense {
    Expense add(ExpenseDTO expense,Long userId);
}
