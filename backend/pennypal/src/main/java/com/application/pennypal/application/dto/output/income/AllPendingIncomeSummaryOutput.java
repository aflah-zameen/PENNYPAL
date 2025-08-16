package com.application.pennypal.application.dto.output.income;

import java.math.BigDecimal;
import java.util.List;

public record AllPendingIncomeSummaryOutput(List<PendingIncomeOutput> pendingIncomeOutputList,
                                            int totalCount,
                                            BigDecimal totalAmount) {
}
