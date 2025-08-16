package com.application.pennypal.application.service.transaction;

import com.application.pennypal.application.dto.output.transaction.ActiveRecurringTransactionOutput;
import com.application.pennypal.application.dto.output.transaction.PendingTransactionSummaryOutput;
import com.application.pennypal.application.dto.output.transaction.TotalTransactionSummaryOutput;
import com.application.pennypal.application.dto.output.transaction.TransactionSummaryOutput;
import com.application.pennypal.application.port.out.repository.RecurringLogRepositoryPort;
import com.application.pennypal.application.port.out.repository.RecurringTransactionRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.port.in.transaction.GetTransactionSummary;
import com.application.pennypal.domain.valueObject.TransactionType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;

@RequiredArgsConstructor
public class GetTransactionSummaryService implements GetTransactionSummary {
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final RecurringLogRepositoryPort recurringLogRepositoryPort;
    private final RecurringTransactionRepositoryPort recurringTransactionRepositoryPort;
    @Override
    public TransactionSummaryOutput execute(String userId, TransactionType transactionType) {
        //current month
        YearMonth currentMonth = YearMonth.now();
        LocalDate currentMonthStartDate = currentMonth.atDay(1);
        LocalDate currentMonthEndDate = currentMonth.atEndOfMonth();

        // TotalIncomeSummary card computation

            //current month calculation
            BigDecimal currentMonthIncomeTotal = transactionRepositoryPort.getTotalAmountForMonthByTransactionType(userId,currentMonthStartDate,currentMonthEndDate,transactionType);


            //previous month calculation
            YearMonth previousMonth = currentMonth.minusMonths(1);
            LocalDate previousMonthStartDate = previousMonth.atDay(1);
            LocalDate previousMonthEndDate = previousMonth.atEndOfMonth();
            BigDecimal previousMonthTotalIncome = transactionRepositoryPort.getTotalAmountForMonthByTransactionType(userId,previousMonthStartDate,previousMonthEndDate,transactionType);

            //growth rate calculation
            Double progressValue = previousMonthTotalIncome.compareTo(BigDecimal.ZERO) > 0 ?currentMonthIncomeTotal.subtract(previousMonthTotalIncome)
                    .divide(previousMonthTotalIncome,2, RoundingMode.HALF_UP)
                    .doubleValue() * 100 : null;

            TotalTransactionSummaryOutput totalTransactionSummaryOutput = new TotalTransactionSummaryOutput(currentMonthIncomeTotal,progressValue);

        // PendingIncomesSummary card computation

            //Pending Incomes Total amount in current month
            PendingTransactionSummaryOutput currentMonthPendingRecurringStats = recurringLogRepositoryPort.getTotalPendingRecurringTransaction(userId,currentMonthStartDate,currentMonthEndDate,transactionType);
            PendingTransactionSummaryOutput pendingSummaryOutput = new PendingTransactionSummaryOutput(currentMonthPendingRecurringStats.totalAmount(),currentMonthPendingRecurringStats.pendingTransactions());
        // Active recurring incomes count

            Integer activeRecurringIncomeCount = recurringTransactionRepositoryPort.countActiveRecurringTransactionByUserId(userId, transactionType);
            ActiveRecurringTransactionOutput activeRecurringOutput = new ActiveRecurringTransactionOutput(activeRecurringIncomeCount);

        return new TransactionSummaryOutput(totalTransactionSummaryOutput,pendingSummaryOutput,activeRecurringOutput);
    }
}
