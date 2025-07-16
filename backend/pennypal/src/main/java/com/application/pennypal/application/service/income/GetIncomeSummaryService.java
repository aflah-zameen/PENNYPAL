package com.application.pennypal.application.service.income;

import com.application.pennypal.application.output.income.*;
import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.application.port.RecurringIncomeLogRepositoryPort;
import com.application.pennypal.application.port.TransactionRepositoryPort;
import com.application.pennypal.application.usecases.Income.GetIncomeSummary;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;

@RequiredArgsConstructor
public class GetIncomeSummaryService implements GetIncomeSummary {
    private final IncomeRepositoryPort incomeRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final RecurringIncomeLogRepositoryPort recurringIncomeLogRepositoryPort;
    @Override
    public IncomeSummaryOutput execute(Long userId) {
        //current month
        YearMonth currentMonth = YearMonth.now();
        LocalDate currentMonthStartDate = currentMonth.atDay(1);
        LocalDate currentMonthEndDate = currentMonth.atEndOfMonth();

        // TotalIncomeSummary card computation

            //current month calculation
            BigDecimal currentMonthIncomeTotal = transactionRepositoryPort.getTotalAmountForMonth(currentMonthStartDate,currentMonthEndDate);


            //previous month calculation
            YearMonth previousMonth = currentMonth.minusMonths(1);
            LocalDate previousMonthStartDate = previousMonth.atDay(1);
            LocalDate previousMonthEndDate = previousMonth.atEndOfMonth();
            BigDecimal previousMonthTotalIncome = transactionRepositoryPort.getTotalAmountForMonth(previousMonthStartDate,previousMonthEndDate);

            //growth rate calculation
            Double progressValue = previousMonthTotalIncome.compareTo(BigDecimal.ZERO) > 0 ?currentMonthIncomeTotal.subtract(previousMonthTotalIncome)
                    .divide(previousMonthTotalIncome,2, RoundingMode.HALF_UP)
                    .doubleValue() * 100 : null;

            TotalIncomeSummaryOutput totalIncomeSummaryOutput = new TotalIncomeSummaryOutput(currentMonthIncomeTotal,progressValue);

        // PendingIncomesSummary card computation

            //Pending Incomes Total amount in current month
            PendingIncomeSummaryOutput currentMonthPendingRecurringIncomeStats = recurringIncomeLogRepositoryPort.getTotalPendingRecurringIncome(userId,currentMonthStartDate,currentMonthEndDate);
            PendingIncomeSummaryOutput currentMonthPendingOneTimeIncomeStats = incomeRepositoryPort.getTotalPendingOneTimeIncome(userId,currentMonthStartDate,currentMonthEndDate);
            Long pendingIncomesCount = currentMonthPendingRecurringIncomeStats.pendingIncomes()+ currentMonthPendingOneTimeIncomeStats.pendingIncomes();
            BigDecimal currentTotalPendingIncomes = currentMonthPendingRecurringIncomeStats.totalAmount().add(currentMonthPendingOneTimeIncomeStats.totalAmount());
            PendingIncomeSummaryOutput pendingIncomeSummaryOutput = new PendingIncomeSummaryOutput(currentTotalPendingIncomes,pendingIncomesCount);
        // Active recurring incomes count

            Integer activeRecurringIncomeCount = incomeRepositoryPort.countActiveRecurringIncomeByUserId(userId);
            ActiveRecurringIncomeOutput activeRecurringIncomeOutput = new ActiveRecurringIncomeOutput(activeRecurringIncomeCount);

        return new IncomeSummaryOutput(totalIncomeSummaryOutput,pendingIncomeSummaryOutput,activeRecurringIncomeOutput);
    }
}
