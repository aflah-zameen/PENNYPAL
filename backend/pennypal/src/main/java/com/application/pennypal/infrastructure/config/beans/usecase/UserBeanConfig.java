package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.port.in.transaction.DashboardIncomeExpenseChart;
import com.application.pennypal.application.port.in.transaction.GetExpenseChart;
import com.application.pennypal.application.port.in.user.CheckUserIsSuspended;
import com.application.pennypal.application.port.in.user.GetContacts;
import com.application.pennypal.application.port.in.user.GetDashboardSummary;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.UpdateUserPort;
import com.application.pennypal.application.service.transaction.DashboardIncomeExpenseChartService;
import com.application.pennypal.application.service.transaction.GetExpenseChartService;
import com.application.pennypal.application.service.user.CheckUserIsSuspendedService;
import com.application.pennypal.application.service.user.GetContactsService;
import com.application.pennypal.application.service.user.GetDashboardSummaryService;
import com.application.pennypal.application.service.user.UpdateUserService;
import com.application.pennypal.application.port.in.user.UpdateUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBeanConfig {


    @Bean
    public UpdateUser updateUser(UpdateUserPort updateUserPort){
        return new UpdateUserService(updateUserPort);
    }

    @Bean
    public GetContacts getContacts(UserRepositoryPort userRepositoryPort, TransactionRepositoryPort transactionRepositoryPort){
        return new GetContactsService(userRepositoryPort,transactionRepositoryPort);
    }

    @Bean
    public GetDashboardSummary getDashboardSummary(TransactionRepositoryPort repositoryPort){
        return new GetDashboardSummaryService(repositoryPort);
    }

    @Bean
    public DashboardIncomeExpenseChart getDashboardChart(TransactionRepositoryPort transactionRepositoryPort){
        return new DashboardIncomeExpenseChartService(transactionRepositoryPort);
    }

    @Bean
    public GetExpenseChart getExpenseChart(TransactionRepositoryPort transactionRepositoryPort){
        return new GetExpenseChartService(transactionRepositoryPort);
    }

    @Bean
    public CheckUserIsSuspended checkUserIsSuspended(UserRepositoryPort userRepositoryPort){
        return new CheckUserIsSuspendedService(userRepositoryPort);
    }
}
