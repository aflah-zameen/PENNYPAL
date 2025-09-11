package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.LoanCase.LoanCase;
import com.application.pennypal.infrastructure.persistence.jpa.LoanCase.LoanCaseEntity;

public class LoanCaseJpaMapper {
    public static LoanCase toDomain (LoanCaseEntity loanCaseEntity){
        return LoanCase.reconstruct(
                loanCaseEntity.getCaseId(),
                loanCaseEntity.getLoanId(),
                loanCaseEntity.getFiledBy(),
                loanCaseEntity.getReason(),
                loanCaseEntity.getFiledAt(),
                loanCaseEntity.getStatus(),
                loanCaseEntity.getAdminNotes(),
                loanCaseEntity.getClosedAt(),
                loanCaseEntity.getUpdatedAt()
        );
    }

    public static LoanCaseEntity toEntity(LoanCase loanCase){
        return new LoanCaseEntity(
                loanCase.getCaseId(),
                loanCase.getLoanId(),
                loanCase.getFiledBy(),
                loanCase.getReason(),
                loanCase.getFiledAt(),
                loanCase.getStatus(),
                loanCase.getAdminNotes(),
                loanCase.getClosedAt()
        );
    }
}
