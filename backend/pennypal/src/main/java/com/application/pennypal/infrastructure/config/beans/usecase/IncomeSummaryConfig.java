package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.port.out.repository.RecurringLogRepositoryPort;
import com.application.pennypal.application.port.out.repository.RecurringTransactionRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.port.in.transaction.GetTransactionSummary;
import com.application.pennypal.application.service.transaction.GetTransactionSummaryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IncomeSummaryConfig {
    @Bean
    public GetTransactionSummary getIncomeSummary(
                                             TransactionRepositoryPort transactionRepositoryPort,
                                             RecurringLogRepositoryPort recurringLogRepositoryPort,
                                             RecurringTransactionRepositoryPort recurringTransactionRepositoryPort
                                             ){
        return new GetTransactionSummaryService(
                transactionRepositoryPort,
                recurringLogRepositoryPort,
                recurringTransactionRepositoryPort
        );
    }
}
