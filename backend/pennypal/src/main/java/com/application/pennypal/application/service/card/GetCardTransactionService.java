package com.application.pennypal.application.service.card;

import com.application.pennypal.application.dto.output.transaction.TransactionOutputModel;
import com.application.pennypal.application.mappers.transaction.TransactionApplicationMapper;
import com.application.pennypal.application.port.in.card.GetCardTransaction;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetCardTransactionService implements GetCardTransaction {
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final TransactionApplicationMapper transactionApplicationMapper;
    @Override
    public List<TransactionOutputModel> getRecent(String cardId, int size) {
        return transactionRepositoryPort.getCardTransaction(cardId,0,size).stream()
                .map(transactionApplicationMapper::toOutput)
                .toList();
    }
}
