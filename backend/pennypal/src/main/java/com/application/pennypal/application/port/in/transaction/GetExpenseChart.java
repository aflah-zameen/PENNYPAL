package com.application.pennypal.application.port.in.transaction;

import com.application.pennypal.application.dto.output.transaction.ExpenseDataChart;

import java.util.List;

public interface GetExpenseChart {
    List<ExpenseDataChart> execute(String userId);
}
