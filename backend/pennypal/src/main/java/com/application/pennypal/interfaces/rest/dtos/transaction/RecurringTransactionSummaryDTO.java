package com.application.pennypal.interfaces.rest.dtos.transaction;

import java.math.BigDecimal;
import java.util.List;

public record RecurringTransactionSummaryDTO(List<RecurringTransactionResponseDTO> recurringTransactions, int count, BigDecimal totalAmount) {
}
