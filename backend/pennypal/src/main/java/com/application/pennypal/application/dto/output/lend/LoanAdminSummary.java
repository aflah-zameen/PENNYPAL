package com.application.pennypal.application.dto.output.lend;

import java.math.BigDecimal;

public record LoanAdminSummary(
        BigDecimal totalLent,
        BigDecimal totalBorrowed,
        long overdueCount,
        long pendingCases
) {
}