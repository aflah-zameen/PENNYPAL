package com.application.pennypal.application.usecases.Income;

import com.application.pennypal.application.dto.RecurringIncomesDataDTO;

public interface GetRecurringIncomesData {
    RecurringIncomesDataDTO execute(Long userId);
}
