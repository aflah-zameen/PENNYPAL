package com.application.pennypal.application.service.transaction;

import com.application.pennypal.application.dto.input.transaction.TransactionInputModel;
import com.application.pennypal.application.dto.output.transaction.TransactionOutputModel;
import com.application.pennypal.application.mappers.transaction.TransactionApplicationMapper;
import com.application.pennypal.application.port.in.transaction.CreateTransaction;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.domain.transaction.entity.Transaction;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateTransactionService implements CreateTransaction {

    private final TransactionRepositoryPort transactionRepositoryPort;
    private final TransactionApplicationMapper transactionApplicationMapper;
    @Override
    public TransactionOutputModel execute(String userId,TransactionInputModel inputModel) {
        Transaction transaction = Transaction.create(
                userId,
                inputModel.categoryId(),
                inputModel.cardId(),
                inputModel.amount(),
                inputModel.transactionType(),
                inputModel.title(),
                inputModel.description(),
                inputModel.paymentMethod(),
                inputModel.transactionDate(),
                false,
                null,
                inputModel.transferToUserId(),
                inputModel.transferFromUserId()

        );
        Transaction createdTransaction = transactionRepositoryPort.save(transaction);
        return transactionApplicationMapper.toOutput(createdTransaction);
    }
}