package com.application.pennypal.application.dto.output.transaction;

public record TransactionSummaryOutput(TotalTransactionSummaryOutput totalTransactionSummaryOutput,
                                       PendingTransactionSummaryOutput pendingTransactionSummaryOutput,
                                       ActiveRecurringTransactionOutput activeRecurringTransactionOutput){
}
