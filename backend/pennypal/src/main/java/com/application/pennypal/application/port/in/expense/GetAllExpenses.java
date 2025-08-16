package com.application.pennypal.application.port.in.expense;

import com.application.pennypal.application.dto.output.expense.ExpenseOutputModel;

import java.util.List;

public interface GetAllExpenses {
    List<ExpenseOutputModel> getAll(Long userId);
}
