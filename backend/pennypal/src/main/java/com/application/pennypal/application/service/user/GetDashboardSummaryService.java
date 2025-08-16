package com.application.pennypal.application.service.user;

import com.application.pennypal.application.dto.output.expense.TotalExpenseSummaryOutput;
import com.application.pennypal.application.dto.output.income.TotalIncomeSummaryOutput;
import com.application.pennypal.application.dto.output.user.DashboardOutputModel;
import com.application.pennypal.application.port.in.user.GetDashboardSummary;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.domain.valueObject.TransactionType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;

@RequiredArgsConstructor
public class GetDashboardSummaryService implements GetDashboardSummary {
    private final TransactionRepositoryPort transactionRepositoryPort;
    @Override
    public DashboardOutputModel execute(String userId) {
        YearMonth currentMonth = YearMonth.now();
        LocalDate currentMonthStartDate = currentMonth.atDay(1);
        LocalDate currentMonthEndDate = currentMonth.atEndOfMonth();
        BigDecimal currentMonthIncomeTotal = transactionRepositoryPort.getTotalAmountForMonthByTransactionType(userId,currentMonthStartDate,currentMonthEndDate, TransactionType.INCOME);
        BigDecimal currentMonthExpenseTotal = transactionRepositoryPort.getTotalAmountForMonthByTransactionType(userId,currentMonthStartDate,currentMonthEndDate, TransactionType.EXPENSE);

        YearMonth previousMonth = currentMonth.minusMonths(1);
        LocalDate previousMonthStartDate = previousMonth.atDay(1);
        LocalDate previousMonthEndDate = previousMonth.atEndOfMonth();
        BigDecimal previousMonthTotalIncome = transactionRepositoryPort.getTotalAmountForMonthByTransactionType(userId,previousMonthStartDate,previousMonthEndDate,TransactionType.INCOME);
        BigDecimal previousMonthTotalExpense = transactionRepositoryPort.getTotalAmountForMonthByTransactionType(userId,previousMonthStartDate,previousMonthEndDate,TransactionType.INCOME);

        Double progressValueIncome = previousMonthTotalIncome.compareTo(BigDecimal.ZERO) > 0 ?currentMonthIncomeTotal.subtract(previousMonthTotalIncome)
                .divide(previousMonthTotalIncome,2, RoundingMode.HALF_UP)
                .doubleValue() * 100 : null;

        Double progressValueExpense = previousMonthTotalExpense.compareTo(BigDecimal.ZERO) > 0 ?currentMonthExpenseTotal.subtract(previousMonthTotalExpense)
                .divide(previousMonthTotalExpense,2, RoundingMode.HALF_UP)
                .doubleValue() * 100 : null;

        TotalIncomeSummaryOutput totalIncomeSummaryOutput = new TotalIncomeSummaryOutput(currentMonthIncomeTotal,progressValueIncome);
        TotalExpenseSummaryOutput totalExpenseSummaryOutput = new TotalExpenseSummaryOutput(currentMonthExpenseTotal,progressValueExpense);
        return new DashboardOutputModel(totalExpenseSummaryOutput,totalIncomeSummaryOutput);
    }
}
