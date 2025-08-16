package com.application.pennypal.application.port.in.wallet;

import com.application.pennypal.application.dto.output.transaction.TransactionOutputModel;

import java.util.List;

public interface GetWalletTransactions {
    List<TransactionOutputModel> execute(String userId);
}
