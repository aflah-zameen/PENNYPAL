package com.application.pennypal.application.service.transaction;

import com.application.pennypal.application.dto.output.transaction.TransactionOutputModel;
import com.application.pennypal.application.mappers.transaction.TransactionApplicationMapper;
import com.application.pennypal.application.port.in.transaction.GetRecentTransactions;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.domain.valueObject.TransactionType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetRecentTransactionService implements GetRecentTransactions {
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final TransactionApplicationMapper transactionApplicationMapper;
    @Override
    public List<TransactionOutputModel> execute(String userId, int size, TransactionType transactionType) {

        //Get all recent transactions
       return  transactionRepositoryPort.findRecentTransaction(userId,size,transactionType).stream()
               .map(transactionApplicationMapper::toOutput)
               .toList();
    }
}
