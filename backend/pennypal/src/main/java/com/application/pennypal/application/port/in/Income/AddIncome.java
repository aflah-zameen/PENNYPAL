package com.application.pennypal.application.port.in.Income;

import com.application.pennypal.application.dto.input.income.IncomeInputModel;
import com.application.pennypal.application.dto.output.income.IncomeOutputModel;

public interface AddIncome {
    IncomeOutputModel add(IncomeInputModel incomeInputModel, Long userId);
}
