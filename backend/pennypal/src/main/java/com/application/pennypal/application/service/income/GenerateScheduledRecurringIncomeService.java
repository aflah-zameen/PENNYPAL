package com.application.pennypal.application.service.income;

import com.application.pennypal.application.port.in.Income.GenerateScheduledRecurringTransaction;
import com.application.pennypal.application.port.out.repository.RecurringLogRepositoryPort;
import com.application.pennypal.application.port.out.repository.RecurringTransactionRepositoryPort;
import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import com.application.pennypal.domain.transaction.entity.RecurringTransactionLog;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
public class GenerateScheduledRecurringIncomeService implements GenerateScheduledRecurringTransaction {
    private final RecurringTransactionRepositoryPort recurringTransactionRepositoryPort;
    private final RecurringLogRepositoryPort recurringLogRepositoryPort;

    @Override
    public void generate() {
        List<RecurringTransaction> activeRecurringTransactions =
                recurringTransactionRepositoryPort.findAllActiveRecurringTransactions();


        for(RecurringTransaction transaction : activeRecurringTransactions){
            LocalDate start = transaction.getStartDate();
            LocalDate end = LocalDate.now();

            //Generate log from start to today
            LocalDate date = start;
            while(!date.isAfter(end)){
                if(shouldGenerateLogForDate(transaction,date)){
                    boolean exists = recurringLogRepositoryPort.existsByRecurringIdAndDateFor(transaction.getRecurringId(),date);
                    if(!exists){
                        recurringLogRepositoryPort.save(
                                RecurringTransactionLog.create(
                                        transaction.getRecurringId(),
                                        transaction.getUserId(),
                                        transaction.getAmount(),
                                        date,
                                        LocalDateTime.now(),
                                        transaction.getTransactionType()
                                )
                        );
                    }
                }
                date = date.plusDays(1);
            }
        }
    }

    private boolean shouldGenerateLogForDate(RecurringTransaction transaction,LocalDate date ){
        return switch (transaction.getFrequency()) {
            case DAILY -> true;
            case WEEKLY -> date.getDayOfWeek() == transaction.getStartDate().getDayOfWeek();
            case MONTHLY -> date.getDayOfMonth() == transaction.getStartDate().getDayOfMonth();
            case YEARLY -> date.getDayOfYear() == transaction.getStartDate().getDayOfYear();
        };
    }
}
