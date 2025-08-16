package com.application.pennypal.application.service.transaction;

import com.application.pennypal.application.dto.input.transaction.EditRecurringTransactionInputModel;
import com.application.pennypal.application.dto.output.transaction.RecurringTransactionOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.mappers.transaction.RecurringTransactionApplicationMapper;
import com.application.pennypal.application.port.in.transaction.EditRecurringTransaction;
import com.application.pennypal.application.port.out.repository.RecurringTransactionRepositoryPort;
import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EditRecurringTransactionService implements EditRecurringTransaction {
    private final RecurringTransactionRepositoryPort repositoryPort;
    private final RecurringTransactionApplicationMapper mapper;

    @Override
    public RecurringTransactionOutputModel execute(String userId,String recurringId,EditRecurringTransactionInputModel inputModel) {
        RecurringTransaction recurringTransaction = repositoryPort.getTransactionByIdAndUser(userId,recurringId)
                .orElseThrow(() -> new ApplicationBusinessException("Recurring transaction entity not found or user is invalid","NOT_FOUND"));
        recurringTransaction = recurringTransaction
                .updateTitle(inputModel.title())
                .updateAmount(inputModel.amount())
                .updateCardId(inputModel.cardId())
                .updateDescription(inputModel.description())
                .extendEndDate(inputModel.endDate());
        RecurringTransaction updatedEntity = repositoryPort.update(recurringTransaction);
        return mapper.toOutput(updatedEntity);
    }
}
