package com.application.pennypal.interfaces.rest.dtos.income;

import java.math.BigDecimal;
import java.util.List;

public record AllPendingIncomeSummaryDTO(List<PendingIncomeResponseDTO> pendingIncomeList, int totalCount, BigDecimal totalAmount) {
}
