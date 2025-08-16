package com.application.pennypal.application.port.in.transaction;

import com.application.pennypal.application.dto.output.transaction.DashIncExpChart;

import java.util.List;

public interface DashboardIncomeExpenseChart {
    List<DashIncExpChart> execute(String userId);
}
