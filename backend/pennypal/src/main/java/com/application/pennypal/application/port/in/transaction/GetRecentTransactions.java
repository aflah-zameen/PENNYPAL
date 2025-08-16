package com.application.pennypal.application.port.in.transaction;

import com.application.pennypal.application.dto.output.transaction.TransactionOutputModel;
import com.application.pennypal.domain.valueObject.TransactionType;

import java.util.List;

public interface GetRecentTransactions {
    List<TransactionOutputModel> execute(String userId, int size, TransactionType transactionType);
}
