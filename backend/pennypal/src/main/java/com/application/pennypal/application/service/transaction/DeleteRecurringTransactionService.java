package com.application.pennypal.application.service.transaction;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.transaction.DeleteRecurringTransaction;
import com.application.pennypal.application.port.out.repository.RecurringTransactionRepositoryPort;
import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteRecurringTransactionService implements DeleteRecurringTransaction {
    private final RecurringTransactionRepositoryPort repositoryPort;
    @Override
    public void execute(String userId, String recurringId) {
        RecurringTransaction transaction =repositoryPort.getTransactionByIdAndUser(userId,recurringId)
                .orElseThrow(() -> new ApplicationBusinessException("User or Recurring transaction entity not found","NOT_FOUND"));
        transaction = transaction.delete();
        repositoryPort.update(transaction);
    }
}
