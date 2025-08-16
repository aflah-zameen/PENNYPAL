package com.application.pennypal.infrastructure.config.beans.mapper;


import com.application.pennypal.application.mappers.goal.GoalContributionApplicationMapper;
import com.application.pennypal.application.mappers.income.IncomeApplicationMapper;
import com.application.pennypal.application.mappers.transaction.RecurringTransactionApplicationMapper;
import com.application.pennypal.application.mappers.transaction.TransactionApplicationMapper;
import com.application.pennypal.application.port.out.repository.CardRepositoryPort;
import com.application.pennypal.application.port.out.repository.CategoryManagementRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationMapperConfig {
    @Bean
    public IncomeApplicationMapper incomeApplicationMapper(CategoryManagementRepositoryPort categoryManagementRepositoryPort){
        return new IncomeApplicationMapper(categoryManagementRepositoryPort);
    }

    @Bean
    public TransactionApplicationMapper applicationMapper(CategoryManagementRepositoryPort categoryPort,
                                                          CardRepositoryPort cardRepositoryPort){
        return new TransactionApplicationMapper(categoryPort, cardRepositoryPort);
    }

    @Bean
    public RecurringTransactionApplicationMapper recurringTransactionApplicationMapper(CategoryManagementRepositoryPort categoryPort,
                                                                                       CardRepositoryPort cardRepositoryPort){
        return new RecurringTransactionApplicationMapper(categoryPort, cardRepositoryPort);
    }

    @Bean
    GoalContributionApplicationMapper goalContributionApplicationMapper(CardRepositoryPort cardRepositoryPort){
        return new GoalContributionApplicationMapper(cardRepositoryPort);
    }

}
