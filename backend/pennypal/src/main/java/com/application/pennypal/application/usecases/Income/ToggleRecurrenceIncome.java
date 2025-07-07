package com.application.pennypal.application.usecases.Income;

import com.application.pennypal.application.dto.RecurringIncomeDTO;

public interface ToggleRecurrenceIncome {
    RecurringIncomeDTO toggle(Long incomeId);
}
