package com.application.pennypal.application.service.transaction;

import com.application.pennypal.application.dto.output.transaction.RecurringTransactionOutputModel;
import com.application.pennypal.application.mappers.transaction.RecurringTransactionApplicationMapper;
import com.application.pennypal.application.port.in.transaction.GetRecurringTransactions;
import com.application.pennypal.application.port.out.repository.RecurringTransactionRepositoryPort;
import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import com.application.pennypal.domain.valueObject.TransactionType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetRecurringTransactionsService implements GetRecurringTransactions {
    private final RecurringTransactionRepositoryPort transactionRepositoryPort;
    private final RecurringTransactionApplicationMapper recurringTransactionApplicationMapper;
    @Override
    public List<RecurringTransactionOutputModel> execute(String userId, TransactionType transactionType) {
        List<RecurringTransaction> list =  transactionRepositoryPort.getAllRecurringTransactions(userId,transactionType);
                return list.stream()
                .map(recurringTransactionApplicationMapper::toOutput)
                .toList();
    }
}
