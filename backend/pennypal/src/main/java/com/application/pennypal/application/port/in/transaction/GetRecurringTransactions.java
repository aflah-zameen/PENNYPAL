package com.application.pennypal.application.port.in.transaction;

import com.application.pennypal.application.dto.output.transaction.RecurringTransactionOutputModel;
import com.application.pennypal.domain.valueObject.TransactionType;

import java.util.List;

public interface GetRecurringTransactions{
    List<RecurringTransactionOutputModel> execute(String userId, TransactionType transactionType);
}
