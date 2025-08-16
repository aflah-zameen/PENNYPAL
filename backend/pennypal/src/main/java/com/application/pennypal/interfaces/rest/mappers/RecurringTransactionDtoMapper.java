package com.application.pennypal.interfaces.rest.mappers;

import com.application.pennypal.application.dto.input.transaction.EditRecurringTransactionInputModel;
import com.application.pennypal.application.dto.input.transaction.RecurringTransactionInputModel;
import com.application.pennypal.application.dto.output.transaction.AllPendingTransactionSummaryOutput;
import com.application.pennypal.application.dto.output.transaction.PendingTransactionOutput;
import com.application.pennypal.application.dto.output.transaction.RecurringTransactionOutputModel;
import com.application.pennypal.domain.valueObject.RecurrenceFrequency;
import com.application.pennypal.domain.valueObject.TransactionType;
import com.application.pennypal.interfaces.rest.dtos.transaction.*;

import java.time.LocalDate;

public class RecurringTransactionDtoMapper {
    public static RecurringTransactionInputModel toInput(AddRecurringTransactionDTO recurringDto){
        return new RecurringTransactionInputModel(
                recurringDto.cardId(),
                recurringDto.categoryId(),
                TransactionType.valueOf(recurringDto.transactionType().toUpperCase()),
                recurringDto.title(),
                recurringDto.description(),
                recurringDto.amount(),
                RecurrenceFrequency.valueOf(recurringDto.frequency().toUpperCase()),
                LocalDate.parse(recurringDto.startDate()),
                LocalDate.parse(recurringDto.endDate())
        );
    }

    public static EditRecurringTransactionInputModel toInput(EditRecurringTransactionDTO transactionDTO){
        return new EditRecurringTransactionInputModel(
                transactionDTO.amount(),
                transactionDTO.title(),
                transactionDTO.cardId(),
                transactionDTO.endDate() != null ? LocalDate.parse(transactionDTO.endDate())  : null,
                transactionDTO.description()
        );
    }

    public static PendingTransactionResponseDTO toResponse(PendingTransactionOutput output){
        return new PendingTransactionResponseDTO(
                output.transactionId(),
                output.title(),
                output.amount(),
                CategoryDtoMapper.toResponse(output.category()),
                output.card() != null ? CardDtoMapper.toResponse(output.card()) : null,
                output.transactionType().getValue(),
                output.description(),
                output.transactionDate(),
                output.status().getValue()
        );
    }

    public static RecurringTransactionResponseDTO toResponse(RecurringTransactionOutputModel outputModel){
        return new RecurringTransactionResponseDTO(
                outputModel.recurringId(),
                outputModel.userId(),
                outputModel.card() != null ? CardDtoMapper.toResponse(outputModel.card()) : null,
                CategoryDtoMapper.toResponse(outputModel.category()),
                outputModel.transactionType().getValue(),
                outputModel.title(),
                outputModel.description(),
                outputModel.amount(),
                outputModel.frequency().getValue(),
                outputModel.startDate(),
                outputModel.endDate(),
                outputModel.lastGeneratedDate(),
                outputModel.active(),
                outputModel.createdAt()
        );
    }

    public static AllPendingTransactionSummaryDTO toResponse(AllPendingTransactionSummaryOutput outputModel){
        return new AllPendingTransactionSummaryDTO(
                outputModel.pendingOutputs().stream()
                        .map(RecurringTransactionDtoMapper::toResponse)
                        .toList()
                ,
                outputModel.totalCount(),
                outputModel.totalAmount()

        );
    }
}
