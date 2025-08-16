package com.application.pennypal.interfaces.rest.dtos.user;


import com.application.pennypal.application.dto.output.expense.TotalExpenseSummaryOutput;
import com.application.pennypal.application.dto.output.income.TotalIncomeSummaryOutput;

public record DashboardSummaryResponse(
        TotalExpenseSummaryOutput expenseSummary,
        TotalIncomeSummaryOutput incomeSummary
) {
}
