package com.application.pennypal.application.port.in.transaction;

import com.application.pennypal.application.dto.input.transaction.RecurringTransactionInputModel;
import com.application.pennypal.application.dto.output.transaction.RecurringTransactionOutputModel;

public interface CreateRecurringTransaction {
    RecurringTransactionOutputModel execute(String userId, RecurringTransactionInputModel model);
}
