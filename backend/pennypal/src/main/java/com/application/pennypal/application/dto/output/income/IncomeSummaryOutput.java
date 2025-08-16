package com.application.pennypal.application.dto.output.income;

public record IncomeSummaryOutput(TotalIncomeSummaryOutput totalIncomeSummaryOutput,
                                  PendingIncomeSummaryOutput pendingIncomeSummaryOutput,
                                  ActiveRecurringIncomeOutput activeRecurringIncomeOutput) {
}
