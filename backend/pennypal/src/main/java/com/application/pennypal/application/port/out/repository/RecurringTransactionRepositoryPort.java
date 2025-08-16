package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.application.dto.output.transaction.RecurringTransactionOutputModel;
import com.application.pennypal.domain.entity.Income;
import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import com.application.pennypal.domain.valueObject.TransactionType;

import java.util.List;
import java.util.Optional;

public interface RecurringTransactionRepositoryPort {
    Integer countActiveRecurringTransactionByUserId(String userId, TransactionType transactionType);
    List<RecurringTransaction> findAllActiveRecurringTransactions();
    Optional<RecurringTransaction> getTransactionByIdAndUser(String userId,String recurringId);
    RecurringTransaction save(RecurringTransaction transaction);
    List<RecurringTransaction> getAllRecurringTransactions(String userId, TransactionType transactionType);
    RecurringTransaction update(RecurringTransaction transaction);
}
