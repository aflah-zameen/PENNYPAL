package com.application.pennypal.infrastructure.config;


import com.application.pennypal.application.mappers.income.IncomeApplicationMapper;
import com.application.pennypal.application.port.CategoryManagementRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationMapperConfig {
    @Bean
    public IncomeApplicationMapper incomeApplicationMapper(CategoryManagementRepositoryPort categoryManagementRepositoryPort){
        return new IncomeApplicationMapper(categoryManagementRepositoryPort);
    }

}
