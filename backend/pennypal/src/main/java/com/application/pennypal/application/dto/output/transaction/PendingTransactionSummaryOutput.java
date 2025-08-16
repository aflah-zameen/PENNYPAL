package com.application.pennypal.application.dto.output.transaction;

import java.math.BigDecimal;

public record PendingTransactionSummaryOutput(BigDecimal totalAmount, Long pendingTransactions) {
}
