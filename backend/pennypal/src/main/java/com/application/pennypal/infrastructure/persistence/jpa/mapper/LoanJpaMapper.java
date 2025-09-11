package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.lend.Loan;
import com.application.pennypal.infrastructure.persistence.jpa.lend.LendingRequestEntity;
import com.application.pennypal.infrastructure.persistence.jpa.lend.LoanEntity;

public class LoanJpaMapper {
    public static Loan toDomain(LoanEntity entity){
        return Loan.reconstruct(
                entity.getLoanId(),
                entity.getLendingRequest().getRequestId(),
                entity.getAmount(),
                entity.getAmountPaid(),
                entity.getDeadline(),
                entity.getAcceptedAt(),
                entity.getStatus(),
                entity.getLastReminderSentAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static LoanEntity toEntity(Loan loan, LendingRequestEntity lendingRequest){
        return new LoanEntity(
                loan.getLoanId(),
                lendingRequest,
                loan.getAmount(),
                loan.getAmountPaid(),
                loan.getAcceptedAt(),
                loan.getDeadline(),
                loan.getStatus(),
                loan.getLastReminderSentAt()
        );
    }

}
