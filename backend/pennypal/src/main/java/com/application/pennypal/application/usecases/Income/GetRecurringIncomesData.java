package com.application.pennypal.application.usecases.Income;

import com.application.pennypal.application.output.income.RecurringIncomesDataOutput;

public interface GetRecurringIncomesData {
    RecurringIncomesDataOutput execute(Long userId);
}
