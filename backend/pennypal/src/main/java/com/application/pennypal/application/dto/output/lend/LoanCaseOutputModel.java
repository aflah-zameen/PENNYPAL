package com.application.pennypal.application.dto.output.lend;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LoanCaseOutputModel(
        String id,
        String loanId,
        String borrowerId,
        String borrowerName,
        String lenderName,
        BigDecimal amount,
        String reason,
        LocalDateTime filedDate,
        String status,
        String adminNotes,
        LocalDateTime resolvedDate,
        LocalDateTime updatedAt
) {
}
