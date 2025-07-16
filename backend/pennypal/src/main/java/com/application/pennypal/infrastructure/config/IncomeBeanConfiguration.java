package com.application.pennypal.infrastructure.config;

import com.application.pennypal.application.mappers.income.IncomeApplicationMapper;
import com.application.pennypal.application.port.CategoryManagementRepositoryPort;
import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.application.port.RecurringIncomeLogRepositoryPort;
import com.application.pennypal.application.port.TransactionRepositoryPort;
import com.application.pennypal.application.service.income.CollectPendingIncomesService;
import com.application.pennypal.application.service.income.GetAllPendingIncomeSummaryService;
import com.application.pennypal.application.service.income.GetRecentIncomeTransactionService;
import com.application.pennypal.application.service.income.IncomeService;
import com.application.pennypal.application.usecases.Income.CollectPendingIncomes;
import com.application.pennypal.application.usecases.Income.GenerateScheduledRecurringIncome;
import com.application.pennypal.application.usecases.Income.GetAllPendingIncomeSummary;
import com.application.pennypal.application.usecases.Income.GetRecentIncomeTransactions;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.Income.RecurringIncomeLogRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IncomeBeanConfiguration {
    @Bean
    public IncomeService incomeService(IncomeRepositoryPort incomeRepositoryPort, IncomeApplicationMapper incomeApplicationMapper,
                                       TransactionRepositoryPort transactionRepositoryPort, GenerateScheduledRecurringIncome generateScheduledRecurringIncome){
        return new IncomeService(
                incomeRepositoryPort,
                incomeApplicationMapper,
                transactionRepositoryPort,
                generateScheduledRecurringIncome
                );
    }
    @Bean
    public GetRecentIncomeTransactions getRecentIncomeTransactions(TransactionRepositoryPort transactionRepositoryPort,
                                                                   IncomeRepositoryPort incomeRepositoryPort,
                                                                   CategoryManagementRepositoryPort categoryManagementRepositoryPort,
                                                                   IncomeApplicationMapper incomeApplicationMapper){
        return new GetRecentIncomeTransactionService(transactionRepositoryPort,incomeRepositoryPort,
                incomeApplicationMapper,categoryManagementRepositoryPort);
    }

    @Bean
    public GetAllPendingIncomeSummary getAllPendingIncomeSummary(RecurringIncomeLogRepositoryPort recurringIncomeLogRepositoryPort,
                                                                 IncomeRepositoryPort incomeRepositoryPort,
                                                                 CategoryManagementRepositoryPort categoryManagementRepositoryPort){
        return new GetAllPendingIncomeSummaryService(incomeRepositoryPort,recurringIncomeLogRepositoryPort,categoryManagementRepositoryPort);
    }

    @Bean
    public CollectPendingIncomes collectPendingIncomes(IncomeRepositoryPort incomeRepositoryPort,
                                                       TransactionRepositoryPort transactionRepositoryPort,
                                                       RecurringIncomeLogRepositoryPort recurringIncomeLogRepositoryPort
                                                       ){
        return new CollectPendingIncomesService(incomeRepositoryPort,transactionRepositoryPort,recurringIncomeLogRepositoryPort);
    }
}
