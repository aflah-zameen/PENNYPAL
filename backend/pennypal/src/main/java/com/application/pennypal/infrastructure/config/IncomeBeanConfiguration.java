package com.application.pennypal.infrastructure.config;

import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.application.service.IncomeService;
import com.application.pennypal.application.usecases.Income.AddIncome;
import com.application.pennypal.application.usecases.Income.GetAllIncomes;
import com.application.pennypal.application.usecases.Income.GetTotalIncome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IncomeBeanConfiguration {
    @Bean
    public IncomeService incomeService(IncomeRepositoryPort incomeRepositoryPort){
        return new IncomeService(incomeRepositoryPort);
    }
}
