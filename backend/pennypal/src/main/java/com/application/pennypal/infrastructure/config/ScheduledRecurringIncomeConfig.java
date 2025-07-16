package com.application.pennypal.infrastructure.config;

import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.application.port.RecurringIncomeLogRepositoryPort;
import com.application.pennypal.application.service.income.GenerateScheduledRecurringIncomeService;
import com.application.pennypal.application.usecases.Income.GenerateScheduledRecurringIncome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduledRecurringIncomeConfig {

    @Bean
    public GenerateScheduledRecurringIncome scheduleRecurringIncomeService(IncomeRepositoryPort incomeRepositoryPort,
                                                                                                          RecurringIncomeLogRepositoryPort recurringIncomeLogRepositoryPort){
        return new GenerateScheduledRecurringIncomeService(
                incomeRepositoryPort,
                recurringIncomeLogRepositoryPort
        );
    }
}
