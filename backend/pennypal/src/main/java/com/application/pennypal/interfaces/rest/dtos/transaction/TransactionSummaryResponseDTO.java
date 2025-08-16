package com.application.pennypal.interfaces.rest.dtos.transaction;

import com.application.pennypal.application.dto.output.transaction.ActiveRecurringTransactionOutput;
import com.application.pennypal.application.dto.output.transaction.PendingTransactionSummaryOutput;
import com.application.pennypal.application.dto.output.transaction.TotalTransactionSummaryOutput;

public record TransactionSummaryResponseDTO(
        TotalTransactionSummaryOutput totalTransactionSummary,
        PendingTransactionSummaryOutput pendingTransactionSummary,
        ActiveRecurringTransactionOutput activeRecurringTransaction
) {
}
