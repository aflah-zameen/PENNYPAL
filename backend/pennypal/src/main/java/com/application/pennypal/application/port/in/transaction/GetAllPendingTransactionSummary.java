package com.application.pennypal.application.port.in.transaction;

import com.application.pennypal.application.dto.output.transaction.AllPendingTransactionSummaryOutput;
import com.application.pennypal.domain.valueObject.TransactionType;

public interface GetAllPendingTransactionSummary {
    AllPendingTransactionSummaryOutput execute(String userId, TransactionType transactionType);
}
