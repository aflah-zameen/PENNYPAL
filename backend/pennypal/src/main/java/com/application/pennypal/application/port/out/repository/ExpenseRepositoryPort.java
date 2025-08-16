package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.domain.entity.Expense;
import com.application.pennypal.domain.valueObject.ExpenseDTO;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepositoryPort {
    Expense save(ExpenseDTO expense,Long userId);
    Expense save(Expense expense);
    List<Expense> fetchAllExpenses(Long userId);

    Optional<Expense> getExpenseById(Long expenseId);
}
