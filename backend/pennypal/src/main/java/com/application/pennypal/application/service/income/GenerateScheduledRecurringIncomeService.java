package com.application.pennypal.application.service.income;

import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.application.port.RecurringIncomeLogRepositoryPort;
import com.application.pennypal.application.usecases.Income.GenerateScheduledRecurringIncome;
import com.application.pennypal.domain.entity.Income;
import com.application.pennypal.domain.entity.RecurringIncomeLog;
import com.application.pennypal.domain.valueObject.RecurringIncomeLogStatus;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
public class GenerateScheduledRecurringIncomeService implements GenerateScheduledRecurringIncome {
    private final IncomeRepositoryPort incomeRepositoryPort;
    private final RecurringIncomeLogRepositoryPort recurringIncomeLogRepositoryPort;

    @Override
    public void generate() {
        List<Income> activeRecurringIncomes =
                incomeRepositoryPort.findAllActiveRecurringIncomes();


        for(Income income : activeRecurringIncomes){
            LocalDate start = income.getStartDate();
            LocalDate end = LocalDate.now();

            //Generate log from start to today
            LocalDate date = start;
            while(!date.isAfter(end)){
                if(shouldGenerateLogForDate(income,date)){
                    boolean exists = recurringIncomeLogRepositoryPort.existsByIncomeIdAndDate(income.getId(),date);
                    if(!exists){
                        recurringIncomeLogRepositoryPort.save(
                                new RecurringIncomeLog(income.getUserId(),income.getAmount(),
                                        date, RecurringIncomeLogStatus.PENDING,false,income.getId())
                        );
                    }
                }
                date = date.plusDays(1);
            }
        }
    }

    private boolean shouldGenerateLogForDate(Income income,LocalDate date ){
        return switch (income.getFrequency()) {
            case DAILY -> true;
            case WEEKLY -> date.getDayOfWeek() == income.getStartDate().getDayOfWeek();
            case MONTHLY -> date.getDayOfMonth() == income.getStartDate().getDayOfMonth();
            case YEARLY -> date.getDayOfYear() == income.getStartDate().getDayOfYear();
        };
    }
}
