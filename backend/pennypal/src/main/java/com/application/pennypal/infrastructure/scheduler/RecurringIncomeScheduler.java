package com.application.pennypal.infrastructure.scheduler;

import com.application.pennypal.application.usecases.Income.GenerateScheduledRecurringIncome;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecurringIncomeScheduler {

    private final GenerateScheduledRecurringIncome scheduleRecurringIncomeService;

    @PostConstruct
    public void runOnStartup(){
        scheduleRecurringIncomeService.generate();
    }

    @Scheduled(cron = "0 5 0 * * *") // every day at 00:005
    public void scheduleDailyIncomeLogGeneration(){
        scheduleRecurringIncomeService.generate();
    }
}
