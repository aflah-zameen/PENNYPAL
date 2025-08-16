package com.application.pennypal.application.service.wallet;

import com.application.pennypal.application.dto.output.transaction.TransactionOutputModel;
import com.application.pennypal.application.mappers.transaction.TransactionApplicationMapper;
import com.application.pennypal.application.port.in.wallet.GetWalletTransactions;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.domain.transaction.entity.Transaction;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetWalletTransactionsService implements GetWalletTransactions {
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final TransactionApplicationMapper mapper;
    @Override
    public List<TransactionOutputModel> execute(String userId) {
        List<Transaction> transactions = transactionRepositoryPort.getWalletTransactions(userId);
        return transactions.stream()
                .map(mapper::toOutput)
                .toList();
    }
}
