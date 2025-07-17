package com.application.pennypal.application.service.income;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.mappers.category.CategoryApplicationMapper;
import com.application.pennypal.application.mappers.income.IncomeApplicationMapper;
import com.application.pennypal.application.output.category.CategoryUserOutput;
import com.application.pennypal.application.output.income.IncomeOutputModel;
import com.application.pennypal.application.output.income.IncomeTransactionOutputModel;
import com.application.pennypal.application.port.CategoryManagementRepositoryPort;
import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.application.port.TransactionRepositoryPort;
import com.application.pennypal.application.usecases.Income.GetRecentIncomeTransactions;
import com.application.pennypal.domain.entity.Category;
import com.application.pennypal.domain.entity.Income;
import com.application.pennypal.domain.entity.Transaction;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GetRecentIncomeTransactionService implements GetRecentIncomeTransactions {
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final IncomeRepositoryPort incomeRepositoryPort;
    private final IncomeApplicationMapper incomeApplicationMapper;
    private final CategoryManagementRepositoryPort categoryManagementRepositoryPort;
    @Override
    public List<IncomeTransactionOutputModel> execute(Long userId, int size) {

        //Get all recent transactions
        List<Transaction> transactions = transactionRepositoryPort.findRecentIncomeTransaction(userId,size);
        List<IncomeTransactionOutputModel> transactionOutputModelList = new ArrayList<>();
        for(Transaction tra : transactions){

            /// Fetching Income entity
            Income income = incomeRepositoryPort.getIncomeById(tra.getOriginId())
                            .orElseThrow(() -> new ApplicationBusinessException("Income Entity cannot be found","NOT_FOUND"));
            IncomeOutputModel incomeOutputModel = incomeApplicationMapper.toOutput(income);

            /// Fetching Category entity
            Category category = categoryManagementRepositoryPort.findById(tra.getCategoryId())
                            .orElseThrow(() -> new ApplicationBusinessException("Category entity cannot be found","NOT_FOUND"));
            CategoryUserOutput categoryUserOutput = CategoryApplicationMapper.toOutput(category);

            /// Add each transaction entries to the list.
            transactionOutputModelList.add(new IncomeTransactionOutputModel(tra.getId(),tra.getAmount(),tra.getTransactionDate(),incomeOutputModel,categoryUserOutput));
        }

        return transactionOutputModelList;
    }
}
