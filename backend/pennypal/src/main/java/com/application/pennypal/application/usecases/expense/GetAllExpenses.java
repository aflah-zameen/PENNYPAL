package com.application.pennypal.application.usecases.expense;

import com.application.pennypal.application.output.expense.ExpenseOutputModel;
import com.application.pennypal.domain.entity.Expense;

import java.util.List;

public interface GetAllExpenses {
    List<ExpenseOutputModel> getAll(Long userId);
}
