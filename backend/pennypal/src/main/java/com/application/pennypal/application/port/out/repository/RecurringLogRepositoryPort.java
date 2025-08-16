package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.application.dto.output.income.PendingIncomeSummaryOutput;
import com.application.pennypal.application.dto.output.transaction.PendingTransactionSummaryOutput;
import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import com.application.pennypal.domain.transaction.entity.RecurringTransactionLog;
import com.application.pennypal.domain.valueObject.TransactionType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface RecurringLogRepositoryPort {
    boolean existsByRecurringIdAndDateFor(String recurringId, LocalDate dateFor);

    RecurringTransactionLog save(RecurringTransactionLog recurringTransactionLog);

//    PendingTransactionSummaryOutput getTotalPendingRecurringIncome(Long userId, LocalDate startDate, LocalDate endDate);

//    List<Income> findAllPendingRecurringIncomeLogs(Long userId,LocalDate date);
    Optional<RecurringTransactionLog> getRecurringTransactionLogByTransactionType(String userId, String transactionId, LocalDate incomeDate, TransactionType transactionType);

    PendingTransactionSummaryOutput getTotalPendingRecurringTransaction(String userId, LocalDate currentMonthStartDate, LocalDate currentMonthEndDate, TransactionType transactionType);

    Optional<RecurringTransactionLog> getRecurringTransactionLogByIdAndUser(String userId, String recurringLogId);

    List<RecurringTransactionLog> findAllPendingRecurringLogs(String userId, TransactionType transactionType);
}
