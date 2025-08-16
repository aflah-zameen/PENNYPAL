package com.application.pennypal.application.service.transaction;

import com.application.pennypal.application.dto.input.transaction.RecurringTransactionInputModel;
import com.application.pennypal.application.dto.output.transaction.RecurringTransactionOutputModel;
import com.application.pennypal.application.mappers.transaction.RecurringTransactionApplicationMapper;
import com.application.pennypal.application.port.in.Income.GenerateScheduledRecurringTransaction;
import com.application.pennypal.application.port.in.transaction.CreateRecurringTransaction;
import com.application.pennypal.application.port.out.repository.RecurringTransactionRepositoryPort;
import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateRecurringTransactionService implements CreateRecurringTransaction {
    private final RecurringTransactionRepositoryPort recurringTransactionRepositoryPort;
    private final RecurringTransactionApplicationMapper recurringMapper;
    private final GenerateScheduledRecurringTransaction generateScheduledRecurringTransaction;
    @Override
    public RecurringTransactionOutputModel execute(String userId, RecurringTransactionInputModel model) {
        RecurringTransaction transaction = RecurringTransaction.create(
                userId,
                model.cardId(),
                model.categoryId(),
                model.transactionType(),
                model.title(),
                model.description(),
                model.amount(),
                model.frequency(),
                model.startDate(),
                model.endDate()
        );
        RecurringTransaction newTransaction = recurringTransactionRepositoryPort.save(transaction);
        generateScheduledRecurringTransaction.generate();
        return recurringMapper.toOutput(newTransaction);
    }
}
