package com.application.pennypal.application.output.income;

import java.math.BigDecimal;
import java.util.List;

public record AllPendingIncomeSummaryOutput(List<PendingIncomeOutput> pendingIncomeOutputList,
                                            int totalCount,
                                            BigDecimal totalAmount) {
}
