package com.application.pennypal.infrastructure.config;

import com.application.pennypal.application.mappers.goal.GoalApplicationMapper;
import com.application.pennypal.application.port.CategoryManagementRepositoryPort;
import com.application.pennypal.application.port.GoalRepositoryPort;
import com.application.pennypal.application.port.TransactionRepositoryPort;
import com.application.pennypal.application.service.goal.*;
import com.application.pennypal.application.usecases.goal.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoalBeanConfig {

    @Bean
    public AddGoal addGoal(GoalApplicationMapper goalApplicationMapper,
                           GoalRepositoryPort goalRepositoryPort,
                           TransactionRepositoryPort transactionRepositoryPort){
        return new AddGoalService(
                goalApplicationMapper,
                goalRepositoryPort,
                transactionRepositoryPort
        );
    }
    @Bean
    public GoalApplicationMapper goalApplicationMapper(CategoryManagementRepositoryPort categoryManagementRepositoryPort){
        return new GoalApplicationMapper(categoryManagementRepositoryPort);
    }

    @Bean
    public GetAllGoals getAllGoals(GoalRepositoryPort goalRepositoryPort,GoalApplicationMapper goalApplicationMapper,TransactionRepositoryPort transactionRepositoryPort){
        return new GetAllGoalsService(goalRepositoryPort,goalApplicationMapper,transactionRepositoryPort);
    }

    @Bean
    public AddContribution addContribution(GoalRepositoryPort goalRepositoryPort, TransactionRepositoryPort transactionRepositoryPort){
        return new AddContributionService(goalRepositoryPort,transactionRepositoryPort);
    }
    @Bean
    public EditGoal editGoal(GoalRepositoryPort goalRepositoryPort,GoalApplicationMapper goalApplicationMapper){
        return new EditGoalService(goalRepositoryPort,goalApplicationMapper);
    }

    @Bean
    public DeleteGoal deleteGoal(GoalRepositoryPort goalRepositoryPort){
        return new DeleteGoalService(goalRepositoryPort);
    }

    @Bean
    public GetGoalSummary getGoalSummary(GoalRepositoryPort goalRepositoryPort){
        return new GetGoalSummaryService(goalRepositoryPort);
    }
}
