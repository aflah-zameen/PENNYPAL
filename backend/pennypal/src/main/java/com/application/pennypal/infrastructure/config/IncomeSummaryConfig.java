package com.application.pennypal.infrastructure.config;

import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.application.port.RecurringIncomeLogRepositoryPort;
import com.application.pennypal.application.port.TransactionRepositoryPort;
import com.application.pennypal.application.service.income.GetIncomeSummaryService;
import com.application.pennypal.application.usecases.Income.GetIncomeSummary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IncomeSummaryConfig {
    @Bean
    public GetIncomeSummary getIncomeSummary(IncomeRepositoryPort incomeRepositoryPort,
                                             TransactionRepositoryPort transactionRepositoryPort,
                                             RecurringIncomeLogRepositoryPort recurringIncomeLogRepositoryPort
                                             ){
        return new GetIncomeSummaryService(
                incomeRepositoryPort,
                transactionRepositoryPort,
                recurringIncomeLogRepositoryPort
        );
    }
}
