package com.application.pennypal.application.dto.output.lend;

import java.math.BigDecimal;

public record LendingSummaryOutputModel(
        BigDecimal totalAmountLent,
        BigDecimal totalAmountBorrowed,
        long activeLoansSent,
        long activeLoansReceived,
        long overdueLoansCount,
        long totalPendingRequests
) {
}
