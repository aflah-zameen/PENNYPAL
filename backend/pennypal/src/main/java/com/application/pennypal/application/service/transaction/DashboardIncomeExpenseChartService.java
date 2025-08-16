package com.application.pennypal.application.service.transaction;

import com.application.pennypal.application.dto.output.transaction.DashIncExpChart;
import com.application.pennypal.application.port.in.transaction.DashboardIncomeExpenseChart;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class DashboardIncomeExpenseChartService implements DashboardIncomeExpenseChart {
    private final TransactionRepositoryPort transactionRepositoryPort;
    @Override
    public List<DashIncExpChart> execute(String userId) {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();
        return  transactionRepositoryPort.getIncomeExpenseBetween(userId,startDate,endDate);
    }
}
