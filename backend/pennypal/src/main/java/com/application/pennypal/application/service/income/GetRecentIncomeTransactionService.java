package com.application.pennypal.application.service.income;

import com.application.pennypal.application.exception.BusinessException;
import com.application.pennypal.application.mappers.category.CategoryApplicationMapper;
import com.application.pennypal.application.mappers.income.IncomeApplicationMapper;
import com.application.pennypal.application.output.category.CategoryUserOutput;
import com.application.pennypal.application.output.income.IncomeOutputModel;
import com.application.pennypal.application.output.income.IncomeTransactionOutputModel;
import com.application.pennypal.application.port.CategoryManagementRepositoryPort;
import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.application.port.TransactionRepositoryPort;
import com.application.pennypal.application.usecases.Income.GetRecentIncomeTransactions;
import com.application.pennypal.application.usecases.category.GetCategoryById;
import com.application.pennypal.domain.entity.Category;
import com.application.pennypal.domain.entity.Income;
import com.application.pennypal.domain.entity.Transactions;
import com.application.pennypal.domain.valueObject.IncomeStatus;
import com.application.pennypal.domain.valueObject.TransactionStatus;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.category.CategoryRepository;
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
        List<Transactions> transactions = transactionRepositoryPort.findRecentIncomeTransaction(userId,size);
        List<IncomeTransactionOutputModel> transactionOutputModelList = new ArrayList<>();
        for(Transactions tra : transactions){

            /// Fetching Income entity
            Income income = incomeRepositoryPort.getIncomeById(tra.getOriginId())
                            .orElseThrow(() -> new BusinessException("Income Entity cannot be found","NOT_FOUND"));
            IncomeOutputModel incomeOutputModel = incomeApplicationMapper.toOutput(income);

            /// Fetching Category entity
            Category category = categoryManagementRepositoryPort.findById(tra.getCategoryId())
                            .orElseThrow(() -> new BusinessException("Category entity cannot be found","NOT_FOUND"));
            CategoryUserOutput categoryUserOutput = CategoryApplicationMapper.toOutput(category);

            /// Add each transaction entries to the list.
            transactionOutputModelList.add(new IncomeTransactionOutputModel(tra.getId(),tra.getAmount(),tra.getTransactionDate(),incomeOutputModel,categoryUserOutput));
        }

        return transactionOutputModelList;
    }
}
