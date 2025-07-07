package com.application.pennypal.application.port;

import com.application.pennypal.domain.user.entity.Expense;
import com.application.pennypal.domain.user.valueObject.ExpenseDTO;

import java.util.List;

public interface ExpenseRepositoryPort {
    Expense save(ExpenseDTO expense,Long userId);
    List<Expense> fectchAllExpenses(Long userId);
}
