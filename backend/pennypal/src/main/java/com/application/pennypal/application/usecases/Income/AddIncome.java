package com.application.pennypal.application.usecases.Income;

import com.application.pennypal.application.input.income.IncomeInputModel;
import com.application.pennypal.application.output.income.IncomeOutputModel;

public interface AddIncome {
    IncomeOutputModel add(IncomeInputModel incomeInputModel, Long userId);
}
