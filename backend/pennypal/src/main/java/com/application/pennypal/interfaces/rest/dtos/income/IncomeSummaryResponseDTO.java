package com.application.pennypal.interfaces.rest.dtos.income;

public record IncomeSummaryResponseDTO(TotalIncomeSummaryDTO totalIncomeSummary,
                                       PendingIncomeSummaryDTO pendingIncomeSummary,
                                       ActiveRecurringIncomeDTO activeRecurringIncome) {
}
