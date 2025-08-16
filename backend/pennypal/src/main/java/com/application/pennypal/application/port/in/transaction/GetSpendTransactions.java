package com.application.pennypal.application.port.in.transaction;

import com.application.pennypal.application.dto.output.transaction.TransactionOutputModel;

import java.util.List;

public interface GetSpendTransactions {
    List<TransactionOutputModel> execute(String userId);
}
