package com.application.pennypal.application.output.income;

import java.math.BigDecimal;

public record PendingIncomeSummaryOutput(BigDecimal totalAmount, Long pendingIncomes) {
}
