package com.application.pennypal.application.service.transaction;

import com.application.pennypal.application.dto.output.transaction.TransactionOutputModel;
import com.application.pennypal.application.mappers.transaction.TransactionApplicationMapper;
import com.application.pennypal.application.port.in.transaction.GetSpendTransactions;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetSpendTransactionService implements GetSpendTransactions {
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final TransactionApplicationMapper transactionApplicationMapper;
    @Override
    public List<TransactionOutputModel> execute(String userId) {
        return transactionRepositoryPort.getAllSpendTransaction(userId).stream()
                .map(transactionApplicationMapper::toOutput)
                .toList();
    }
}
