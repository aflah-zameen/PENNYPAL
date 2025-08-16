package com.application.pennypal.interfaces.rest.dtos.transaction;

import java.math.BigDecimal;
import java.util.List;

public record AllPendingTransactionSummaryDTO(List<PendingTransactionResponseDTO> pendingTransactions, int count,
                                              BigDecimal totalAmount) {
}
