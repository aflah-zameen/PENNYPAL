package com.application.pennypal.domain.LoanCase;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoanCase {
    private final String caseId;
    private final String loanId;
    private final String filedBy;
    private final String reason;
    private final LocalDateTime filedAt;
    @Setter
    private LoanCaseStatus status;
    @Setter
    private String adminNotes;
    @Setter
    private LocalDateTime closedAt;
    private final LocalDateTime updatedAt;

    /// Factory method to create

    public static LoanCase create(
            String loanId,
            String filedBy,
            String reason,
            LocalDateTime filedAt
    ){
        String caseId = "CASE_"+ UUID.randomUUID();
        return new LoanCase(
                caseId,
                loanId,
                filedBy,
                reason,
                filedAt,
                LoanCaseStatus.OPEN,
                null,
                null,
                null
        );
    }

    public static LoanCase reconstruct(
            String caseId,
            String loanId,
            String filedBy,
            String reason,
            LocalDateTime filedAt,
            LoanCaseStatus status,
            String adminNotes,
            LocalDateTime closedAt,
            LocalDateTime updatedAt
    ){
        return new LoanCase(
                caseId,
                loanId,
                filedBy,
                reason,
                filedAt,
                status,
                adminNotes,
                closedAt,
                updatedAt
        );
    }

}
