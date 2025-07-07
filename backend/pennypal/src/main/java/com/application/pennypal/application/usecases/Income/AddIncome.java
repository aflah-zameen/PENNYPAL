package com.application.pennypal.application.usecases.Income;

import com.application.pennypal.domain.user.entity.Income;
import com.application.pennypal.domain.user.valueObject.IncomeDTO;

public interface AddIncome {
    Income add(IncomeDTO incomeDTO, Long userId);
}
