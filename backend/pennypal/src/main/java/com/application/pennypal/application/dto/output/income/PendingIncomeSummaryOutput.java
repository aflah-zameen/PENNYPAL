package com.application.pennypal.application.dto.output.income;

import java.math.BigDecimal;

public record PendingIncomeSummaryOutput(BigDecimal totalAmount, Long pendingIncomes) {
}
