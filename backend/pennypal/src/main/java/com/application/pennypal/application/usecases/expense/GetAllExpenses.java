package com.application.pennypal.application.usecases.expense;

import com.application.pennypal.domain.user.entity.Expense;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface GetAllExpenses {
    List<Expense> getAll(Long userId);
}
