package com.application.pennypal.application.usecases.Income;

import java.time.LocalDate;

public interface CollectPendingIncomes {
    void collect(Long userId, Long incomeId, LocalDate incomeDate);
}
