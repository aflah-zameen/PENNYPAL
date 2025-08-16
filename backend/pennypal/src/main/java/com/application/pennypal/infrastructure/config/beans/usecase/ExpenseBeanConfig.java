package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.mappers.expense.ExpenseApplicationMapper;
import com.application.pennypal.application.port.out.repository.CategoryManagementRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpenseBeanConfig {
//    @Bean
//    public AddExpense addExpense(ExpenseRepositoryPort expenseRepositoryPort,ExpenseApplicationMapper applicationMapper){
//        return new ExpenseService(expenseRepositoryPort,applicationMapper);
//    }

//    @Bean
//    public GetAllExpenses getAllExpense(ExpenseRepositoryPort expenseRepositoryPort){
//        return new ExpenseService(expenseRepositoryPort);
//    }
    @Bean
    public ExpenseApplicationMapper expenseApplicationMapper(CategoryManagementRepositoryPort categoryManagementRepositoryPort){
        return new ExpenseApplicationMapper(categoryManagementRepositoryPort);
    }
}
