package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.mappers.transaction.RecurringTransactionApplicationMapper;
import com.application.pennypal.application.mappers.transaction.TransactionApplicationMapper;
import com.application.pennypal.application.port.in.Income.GenerateScheduledRecurringTransaction;
import com.application.pennypal.application.port.in.transaction.*;
import com.application.pennypal.application.port.out.repository.CardRepositoryPort;
import com.application.pennypal.application.port.out.repository.RecurringLogRepositoryPort;
import com.application.pennypal.application.port.out.repository.RecurringTransactionRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.port.out.service.EncodePasswordPort;
import com.application.pennypal.application.service.transaction.*;
import com.application.pennypal.infrastructure.persistence.jpa.transaction.RecurringTransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionBeanCreation {
    @Bean
    CreateTransaction createTransaction(TransactionRepositoryPort transactionRepositoryPort,
                                        TransactionApplicationMapper transactionApplicationMapper){
        return new CreateTransactionService(transactionRepositoryPort,transactionApplicationMapper);
    }

    @Bean
    CreateRecurringTransaction createRecurringTransaction(RecurringTransactionRepositoryPort recurringTransactionRepositoryPort,
                                                          RecurringTransactionApplicationMapper recurringTransactionApplicationMapper,
                                                          GenerateScheduledRecurringTransaction generateScheduledRecurringTransaction){
        return new CreateRecurringTransactionService(recurringTransactionRepositoryPort,recurringTransactionApplicationMapper,generateScheduledRecurringTransaction);
    }

    @Bean
    GetAllPendingTransactionSummary getAllPendingTransactionSummary(RecurringLogRepositoryPort logRepositoryPort,RecurringTransactionRepositoryPort transactionRepositoryPort, RecurringTransactionApplicationMapper recurringTransactionApplicationMapper){
        return new GetAllPendingTransactionSummaryService(logRepositoryPort,
                transactionRepositoryPort,recurringTransactionApplicationMapper);
    }

    @Bean
    public GetRecentTransactions getRecentIncomeTransactions(TransactionRepositoryPort transactionRepositoryPort,
                                                             TransactionApplicationMapper transactionApplicationMapper){
        return new GetRecentTransactionService(transactionRepositoryPort,transactionApplicationMapper
                );
    }

    @Bean
    public GetRecurringTransactions getRecurringTransactions(RecurringTransactionRepositoryPort repositoryPort,
                                                             RecurringTransactionApplicationMapper mapper){
        return new GetRecurringTransactionsService(repositoryPort,mapper);
    }

    @Bean
    public ToggleRecurringTransaction toggleRecurringTransaction(RecurringTransactionRepositoryPort repositoryPort){
        return new ToggleRecurringTransactionService(repositoryPort);
    }

    @Bean
    public DeleteRecurringTransaction deleteRecurringTransaction(RecurringTransactionRepositoryPort repositoryPort){
        return new DeleteRecurringTransactionService(repositoryPort);
    }

    @Bean
    public EditRecurringTransaction editRecurringTransaction(RecurringTransactionRepositoryPort repositoryPort,
                                                             RecurringTransactionApplicationMapper mapper){
        return new EditRecurringTransactionService(repositoryPort,mapper);
    }

    @Bean
    public GetSpendTransactions getSpendTransactions (TransactionRepositoryPort transactionRepositoryPort,
                                                      TransactionApplicationMapper transactionApplicationMapper){
        return new GetSpendTransactionService(transactionRepositoryPort,transactionApplicationMapper);
    }

    @Bean
    public GetSpendSummary getSpendSummary(TransactionRepositoryPort transactionRepositoryPort){
        return new GetSpendSummaryService(transactionRepositoryPort);
    }

    @Bean
    public TransferMoney transferMoney(CardRepositoryPort cardRepositoryPort,
                                       EncodePasswordPort encodePasswordPort,
                                       TransactionRepositoryPort transactionRepositoryPort){
        return new TransferMoneyService(cardRepositoryPort,encodePasswordPort,transactionRepositoryPort);
    }
}
