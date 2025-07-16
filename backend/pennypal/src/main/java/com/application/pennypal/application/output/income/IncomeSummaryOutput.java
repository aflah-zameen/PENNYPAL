package com.application.pennypal.application.output.income;

public record IncomeSummaryOutput(TotalIncomeSummaryOutput totalIncomeSummaryOutput,
                                  PendingIncomeSummaryOutput pendingIncomeSummaryOutput,
                                  ActiveRecurringIncomeOutput activeRecurringIncomeOutput) {
}
