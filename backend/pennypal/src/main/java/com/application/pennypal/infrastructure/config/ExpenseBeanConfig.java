package com.application.pennypal.infrastructure.config;

import com.application.pennypal.application.port.ExpenseRepositoryPort;
import com.application.pennypal.application.service.expense.ExpenseService;
import com.application.pennypal.application.usecases.expense.AddExpense;
import com.application.pennypal.application.usecases.expense.GetAllExpenses;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpenseBeanConfig {
    @Bean
    public AddExpense addExpense(ExpenseRepositoryPort expenseRepositoryPort){
        return new ExpenseService(expenseRepositoryPort);
    }

//    @Bean
//    public GetAllExpenses getAllExpense(ExpenseRepositoryPort expenseRepositoryPort){
//        return new ExpenseService(expenseRepositoryPort);
//    }
}
