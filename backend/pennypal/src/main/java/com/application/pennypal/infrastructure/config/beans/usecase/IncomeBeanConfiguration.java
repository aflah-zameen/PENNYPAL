package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.port.in.transaction.CollectPendingTransaction;
import com.application.pennypal.application.port.out.repository.RecurringLogRepositoryPort;
import com.application.pennypal.application.port.out.repository.RecurringTransactionRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.service.transaction.CollectPendingTransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IncomeBeanConfiguration {
//    @Bean
//    public IncomeService incomeService(IncomeRepositoryPort incomeRepositoryPort, IncomeApplicationMapper incomeApplicationMapper,
//                                       TransactionRepositoryPort transactionRepositoryPort, GenerateScheduledRecurringIncome generateScheduledRecurringIncome){
//        return new IncomeService(
//                incomeRepositoryPort,
//                incomeApplicationMapper,
//                transactionRepositoryPort,
//                generateScheduledRecurringIncome
//                );
//    }
//    @Bean
//    public GetRecentIncomeTransactions getRecentIncomeTransactions(TransactionRepositoryPort transactionRepositoryPort,
//                                                                   IncomeRepositoryPort incomeRepositoryPort,
//                                                                   CategoryManagementRepositoryPort categoryManagementRepositoryPort,
//                                                                   IncomeApplicationMapper incomeApplicationMapper){
//        return new GetRecentIncomeTransactionService(transactionRepositoryPort,incomeRepositoryPort,
//                incomeApplicationMapper,categoryManagementRepositoryPort);
//    }

//    @Bean
//    public GetAllPendingTransactionSummary getAllPendingIncomeSummary(RecurringLogRepositoryPort recurringIncomeLogRepositoryPort,
//                                                                 IncomeRepositoryPort incomeRepositoryPort,
//                                                                 CategoryManagementRepositoryPort categoryManagementRepositoryPort){
//        return new GetAllPendingTransactionSummaryService(incomeRepositoryPort,recurringIncomeLogRepositoryPort,categoryManagementRepositoryPort);
//    }

//    @Bean
//    public CollectPendingIncomes collectPendingIncomes(IncomeRepositoryPort incomeRepositoryPort,
//                                                       TransactionRepositoryPort transactionRepositoryPort,
//                                                       RecurringLogRepositoryPort recurringLogRepositoryPort
//                                                       ){
//        return new CollectPendingIncomesService(incomeRepositoryPort,transactionRepositoryPort, recurringLogRepositoryPort);
//    }

    @Bean
    public CollectPendingTransaction collectPendingIncomes(TransactionRepositoryPort transactionRepositoryPort,
                                                           RecurringTransactionRepositoryPort recurringtransactionRepositoryPort,
                                                           RecurringLogRepositoryPort recurringLogRepositoryPort){
        return new CollectPendingTransactionService(transactionRepositoryPort,recurringtransactionRepositoryPort,recurringLogRepositoryPort);
    }
}
