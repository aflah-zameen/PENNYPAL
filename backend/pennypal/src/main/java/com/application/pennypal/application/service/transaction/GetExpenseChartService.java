package com.application.pennypal.application.service.transaction;

import com.application.pennypal.application.dto.output.transaction.ExpenseDataChart;
import com.application.pennypal.application.port.in.transaction.GetExpenseChart;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class GetExpenseChartService implements GetExpenseChart {
    private final TransactionRepositoryPort transactionRepositoryPort;
    @Override
    public List<ExpenseDataChart> execute(String userId) {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();
        return transactionRepositoryPort.getExpenseBreakdownData(userId,startDate,endDate);
    }
}
