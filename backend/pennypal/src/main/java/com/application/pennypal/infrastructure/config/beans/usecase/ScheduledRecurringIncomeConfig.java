package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.port.in.Income.GenerateScheduledRecurringTransaction;
import com.application.pennypal.application.port.out.repository.RecurringLogRepositoryPort;
import com.application.pennypal.application.port.out.repository.RecurringTransactionRepositoryPort;
import com.application.pennypal.application.service.income.GenerateScheduledRecurringIncomeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduledRecurringIncomeConfig {

    @Bean
    public GenerateScheduledRecurringTransaction scheduleRecurringIncomeService(RecurringTransactionRepositoryPort recurringTransactionRepositoryPort,
                                                                                RecurringLogRepositoryPort recurringIncomeLogRepositoryPort){
        return new GenerateScheduledRecurringIncomeService(
                recurringTransactionRepositoryPort,
                recurringIncomeLogRepositoryPort
        );
    }
}
