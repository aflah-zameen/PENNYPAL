package com.application.pennypal.interfaces.rest.dtos.income;

import java.math.BigDecimal;

public record PendingIncomeSummaryDTO(BigDecimal totalAmount, Long pendingIncomes) {
}
