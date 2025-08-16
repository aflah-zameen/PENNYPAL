package com.application.pennypal.application.dto.output.user;

import com.application.pennypal.application.dto.output.expense.TotalExpenseSummaryOutput;
import com.application.pennypal.application.dto.output.income.TotalIncomeSummaryOutput;

public record DashboardOutputModel(
        TotalExpenseSummaryOutput expenseOutput,
        TotalIncomeSummaryOutput incomeOutput
) {
}
