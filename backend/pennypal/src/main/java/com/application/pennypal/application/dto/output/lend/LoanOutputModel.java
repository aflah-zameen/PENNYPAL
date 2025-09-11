package com.application.pennypal.application.dto.output.lend;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LoanOutputModel (
        String id,
        String lenderId,
        String lenderName,
        String borrowerId,
        String borrowerName,
        BigDecimal amount,
        LocalDateTime loanDate,
        LocalDateTime repaymentDeadline,
        String repaymentStatus,
        BigDecimal amountPaid,
        BigDecimal remainingAmount,
        boolean isOverdue,
        long daysPastDue,
        LocalDateTime lastReminderSentAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
