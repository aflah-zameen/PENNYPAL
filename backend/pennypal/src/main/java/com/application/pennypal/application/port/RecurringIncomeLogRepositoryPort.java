package com.application.pennypal.application.port;

import com.application.pennypal.application.output.income.PendingIncomeSummaryOutput;
import com.application.pennypal.domain.entity.Income;
import com.application.pennypal.domain.entity.RecurringIncomeLog;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RecurringIncomeLogRepositoryPort {
    boolean existsByIncomeIdAndDate(Long id, LocalDate date);

    RecurringIncomeLog save(RecurringIncomeLog recurringIncomeLog);

    PendingIncomeSummaryOutput getTotalPendingRecurringIncome(Long userId, LocalDate startDate, LocalDate endDate);

    List<Income> findAllPendingRecurringIncomeLogs(Long userId,LocalDate date);

    Optional<RecurringIncomeLog> getRecurringIncomeLog(Long userId, Long id, LocalDate incomeDate);

    void update(RecurringIncomeLog recurringIncomeLog);
}
