package com.application.pennypal.interfaces.rest.mappers;

import com.application.pennypal.application.dto.input.transaction.TransactionInputModel;
import com.application.pennypal.application.dto.output.transaction.*;
import com.application.pennypal.domain.valueObject.PaymentMethod;
import com.application.pennypal.domain.valueObject.TransactionType;
import com.application.pennypal.interfaces.rest.dtos.transaction.AddTransactionDTO;
import com.application.pennypal.interfaces.rest.dtos.transaction.TransactionResponseDTO;
import com.application.pennypal.interfaces.rest.dtos.transaction.TransactionSummaryResponseDTO;

import java.time.LocalDate;

public class TransactionDtoMapper {
    public static TransactionInputModel toInput(AddTransactionDTO addTransactionDTO){
        return new TransactionInputModel(
                addTransactionDTO.amount(),
                addTransactionDTO.title(),
                TransactionType.valueOf(addTransactionDTO.transactionType().toUpperCase()),
                addTransactionDTO.cardId(),
                addTransactionDTO.categoryId(),
                LocalDate.parse(addTransactionDTO.transactionDate()),
                addTransactionDTO.description(),
                PaymentMethod.valueOf(addTransactionDTO.paymentMethod().toUpperCase()),
                addTransactionDTO.transferToUserId(),
                addTransactionDTO.transferFromUserId()
        );
    }

    public static TransactionResponseDTO toResponse(TransactionOutputModel outputModel){
        return new TransactionResponseDTO(
                outputModel.transactionId(),
                outputModel.userId(),
                CategoryDtoMapper.toResponse(outputModel.category()),
                outputModel.cardId() != null ? CardDtoMapper.toResponse(outputModel.cardId()):null,
                outputModel.amount(),
                outputModel.transactionType().getValue(),
                outputModel.title(),
                outputModel.description(),
                outputModel.paymentMethod() != null ? outputModel.paymentMethod().getValue():null,
                outputModel.transactionDate(),
                outputModel.isFromRecurring(),
                outputModel.recurringTransactionId(),
                outputModel.transferToUserId(),
                outputModel.transferFromUserId(),
                outputModel.createdAt()
        );
    }

    public static TransactionSummaryResponseDTO toResponse(TransactionSummaryOutput transactionSummaryOutput){
        return new TransactionSummaryResponseDTO(
                new TotalTransactionSummaryOutput(
                        transactionSummaryOutput.totalTransactionSummaryOutput().totalAmount(),
                        transactionSummaryOutput.totalTransactionSummaryOutput().progressValue()

                ),
                new PendingTransactionSummaryOutput(
                        transactionSummaryOutput.pendingTransactionSummaryOutput().totalAmount(),
                        transactionSummaryOutput.pendingTransactionSummaryOutput().pendingTransactions()
                ),
                new ActiveRecurringTransactionOutput(
                        transactionSummaryOutput.activeRecurringTransactionOutput().count()
                )
        );
    }
}
