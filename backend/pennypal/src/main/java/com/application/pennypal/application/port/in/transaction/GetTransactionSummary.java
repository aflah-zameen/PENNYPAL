package com.application.pennypal.application.port.in.transaction;

import com.application.pennypal.application.dto.output.transaction.TransactionSummaryOutput;
import com.application.pennypal.domain.valueObject.TransactionType;

public interface GetTransactionSummary {
    TransactionSummaryOutput execute(String userId, TransactionType transactionType);
}
