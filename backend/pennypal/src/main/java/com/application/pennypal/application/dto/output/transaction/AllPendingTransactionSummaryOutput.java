package com.application.pennypal.application.dto.output.transaction;

import java.math.BigDecimal;
import java.util.List;

public record AllPendingTransactionSummaryOutput(List<PendingTransactionOutput> pendingOutputs,
                                                 int totalCount,
                                                 BigDecimal totalAmount) {
}
