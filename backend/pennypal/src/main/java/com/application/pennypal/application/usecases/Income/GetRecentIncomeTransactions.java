package com.application.pennypal.application.usecases.Income;

import com.application.pennypal.application.output.income.IncomeTransactionOutputModel;

import java.util.List;

public interface GetRecentIncomeTransactions {
    List<IncomeTransactionOutputModel> execute(Long userId, int size);
}
