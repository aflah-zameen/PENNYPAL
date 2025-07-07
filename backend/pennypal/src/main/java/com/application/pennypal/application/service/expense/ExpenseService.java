package com.application.pennypal.application.service.expense;

import com.application.pennypal.application.port.ExpenseRepositoryPort;
import com.application.pennypal.application.usecases.expense.AddExpense;
import com.application.pennypal.application.usecases.expense.GetAllExpenses;
import com.application.pennypal.domain.user.entity.Expense;
import com.application.pennypal.domain.user.valueObject.ExpenseDTO;
import lombok.RequiredArgsConstructor;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RequiredArgsConstructor
public class ExpenseService implements AddExpense, GetAllExpenses {
    private final ExpenseRepositoryPort expenseRepositoryPort;
    @Override
    public Expense add(ExpenseDTO expense,Long userId) {
        return expenseRepositoryPort.save(expense,userId);
    }

    @Override
    public List<Expense> getAll(Long userId) {
        return expenseRepositoryPort.fectchAllExpenses(userId);
    }
}
