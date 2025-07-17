package com.application.pennypal.application.service.income;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.application.port.RecurringIncomeLogRepositoryPort;
import com.application.pennypal.application.port.TransactionRepositoryPort;
import com.application.pennypal.application.usecases.Income.CollectPendingIncomes;
import com.application.pennypal.domain.entity.Income;
import com.application.pennypal.domain.entity.RecurringIncomeLog;
import com.application.pennypal.domain.entity.Transaction;
import com.application.pennypal.domain.valueObject.RecurringStatus;
import com.application.pennypal.domain.valueObject.RecurringIncomeLogStatus;
import com.application.pennypal.domain.valueObject.TransactionType;
import com.application.pennypal.domain.valueObject.TransactionStatus;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class CollectPendingIncomesService implements CollectPendingIncomes {
    private final IncomeRepositoryPort incomeRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final RecurringIncomeLogRepositoryPort recurringIncomeLogRepositoryPort;

    @Override
    public void collect(Long userId, Long incomeId, LocalDate incomeDate) {
        
        Income income = incomeRepositoryPort.getIncomeById(incomeId)
                .orElseThrow(() -> new ApplicationBusinessException("Income entity not found","NOT_FOUND"));

        /// Check the user is authenticated
        if(income.getUserId().equals(userId)){
            /// Complete transaction for pending recurring income
            if(income.getIsRecurring()){
                RecurringIncomeLog recurringIncomeLog = recurringIncomeLogRepositoryPort.getRecurringIncomeLog(userId,income.getId(),incomeDate)
                        .orElseThrow(() -> new ApplicationBusinessException("Recurring income log cannot be found","NOT_FOUND"));
                recurringIncomeLog.setStatus(RecurringIncomeLogStatus.RECEIVED);
                recurringIncomeLogRepositoryPort.update(recurringIncomeLog);
                /// Get new newTransaction
                Transaction newTransaction = getNewTransaction(income,recurringIncomeLog.getDate());
                transactionRepositoryPort.save(newTransaction);
            }
            else{
            /// Managing non recurring incomes
                income.setStatus(RecurringStatus.COMPLETED);
                incomeRepositoryPort.update(income);
                /// Get new newTransaction
                Transaction newTransaction = getNewTransaction(income,income.getIncomeDate());
                transactionRepositoryPort.save(newTransaction);
            }
        }else{
            throw new ApplicationBusinessException("User action is not authenticated","UNAUTHORIZED_ACTION");
        }
    }

    private Transaction getNewTransaction(Income income, LocalDate paymentDate){
        String referenceId = income.getIsRecurring()
                ? "recurring-income-" + income.getId()
                : "manual-income-" + income.getId();
        return new Transaction(
                income.getUserId(),
                income.getAmount(),
                paymentDate,
                TransactionType.INCOME,
                income.getId(),
                TransactionStatus.COMPLETED,
                income.getCategoryId(),
                "Income is collected successfully",
                null,
                income.getIsRecurring(),
                income.getId(),
                referenceId,
                null
        );
    }
}
