package com.application.pennypal.application.usecases.Income;

import com.application.pennypal.domain.user.valueObject.IncomeDTO;

public interface AddIncome {
    IncomeDTO add(IncomeDTO incomeDTO,Long userId);
}
