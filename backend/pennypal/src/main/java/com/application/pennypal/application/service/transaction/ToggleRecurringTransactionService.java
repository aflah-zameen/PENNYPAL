package com.application.pennypal.application.service.transaction;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.transaction.ToggleRecurringTransaction;
import com.application.pennypal.application.port.out.repository.RecurringTransactionRepositoryPort;
import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ToggleRecurringTransactionService implements ToggleRecurringTransaction {
    private final RecurringTransactionRepositoryPort recurringTransactionRepositoryPort;

    @Override
    public void execute(String userId, String recurringId) {
        /// Authenticate entity owner
        RecurringTransaction transaction = recurringTransactionRepositoryPort.getTransactionByIdAndUser(userId,recurringId)
                .orElseThrow(() -> new ApplicationBusinessException("Recurring transaction entity not found","NOT_FOUND"));
        transaction = transaction.toggleStatus();
        recurringTransactionRepositoryPort.update(transaction);
    }
}
