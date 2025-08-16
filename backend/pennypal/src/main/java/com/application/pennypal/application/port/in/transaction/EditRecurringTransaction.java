package com.application.pennypal.application.port.in.transaction;

import com.application.pennypal.application.dto.input.transaction.EditRecurringTransactionInputModel;
import com.application.pennypal.application.dto.input.transaction.RecurringTransactionInputModel;
import com.application.pennypal.application.dto.output.transaction.RecurringTransactionOutputModel;
import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import com.application.pennypal.interfaces.rest.dtos.transaction.RecurringTransactionResponseDTO;

public interface EditRecurringTransaction {
    RecurringTransactionOutputModel execute(String userId,String recurringId,EditRecurringTransactionInputModel inputModel);
}
