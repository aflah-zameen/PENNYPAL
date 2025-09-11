package com.application.pennypal.application.mappers.lent;

import com.application.pennypal.application.dto.output.lend.LendingRequestOutputModel;
import com.application.pennypal.application.dto.output.lend.LoanOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.out.repository.LendingRequestRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.domain.lend.LendingRequest;
import com.application.pennypal.domain.lend.Loan;
import com.application.pennypal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
public class LentApplicationMapper {
    private final UserRepositoryPort userRepositoryPort;
    private final LendingRequestRepositoryPort lendingRequestRepositoryPort;
    public LendingRequestOutputModel toOutput(LendingRequest lendingRequest){
        User sender = userRepositoryPort.findByUserId(lendingRequest.getRequestedBy())
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));
        User receiver = userRepositoryPort.findByUserId(lendingRequest.getRequestedTo())
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));
        return new LendingRequestOutputModel(
                lendingRequest.getRequestId(),
                sender.getUserId(),
                sender.getName(),
                receiver.getUserId(),
                receiver.getName(),
                lendingRequest.getAmount(),
                lendingRequest.getMessage(),
                lendingRequest.getProposedDeadline(),
                lendingRequest.getAcceptedDeadline(),
                lendingRequest.getStatus().getValue(),
                lendingRequest.getCreatedAt(),
                lendingRequest.getUpdatedAt()
        );
    }

    public LoanOutputModel toOutput(Loan loan){
        LendingRequest request = lendingRequestRepositoryPort.findByRequestId(loan.getRequestId())
                .orElseThrow(() -> new ApplicationBusinessException("Lending Request cannot be found","NOT_FOUND"));
        User borrower = userRepositoryPort.findByUserId(request.getRequestedBy())
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));
        User lender = userRepositoryPort.findByUserId(request.getRequestedTo())
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));

        return new LoanOutputModel(
                loan.getLoanId(),
                lender.getUserId(),
                lender.getName(),
                borrower.getUserId(),
                borrower.getName(),
                loan.getAmount(),
                loan.getAcceptedAt(),
                loan.getDeadline(),
                loan.getStatus().name(),
                loan.getAmountPaid(),
                loan.getAmount().subtract(loan.getAmountPaid()),
                loan.isOverdue(LocalDateTime.now()),
                calculateDaysPastDue(loan.getDeadline(),LocalDateTime.now()),
                loan.getLastReminderSentAt(),
                loan.getCreatedAt(),
                loan.getUpdatedAt()
        );
    }

    private long calculateDaysPastDue(LocalDateTime deadline, LocalDateTime now) {
        if (now.isAfter(deadline)) {
            return ChronoUnit.DAYS.between(deadline.toLocalDate(), now.toLocalDate());
        }
        return 0;
    }

}
