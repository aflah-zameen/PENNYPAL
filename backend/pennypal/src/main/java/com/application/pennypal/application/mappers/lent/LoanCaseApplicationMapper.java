package com.application.pennypal.application.mappers.lent;

import com.application.pennypal.application.dto.output.lend.LoanCaseOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.out.repository.LendingRequestRepositoryPort;
import com.application.pennypal.application.port.out.repository.LoanRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.domain.LoanCase.LoanCase;
import com.application.pennypal.domain.lend.LendingRequest;
import com.application.pennypal.domain.lend.Loan;
import com.application.pennypal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoanCaseApplicationMapper {
    private final UserRepositoryPort userRepositoryPort;
    private final LoanRepositoryPort loanRepositoryPort;
    private final LendingRequestRepositoryPort lendingRequestRepositoryPort;
    public LoanCaseOutputModel toOutput(LoanCase loanCase){
        Loan loan = loanRepositoryPort.getLoan(loanCase.getLoanId())
                .orElseThrow(() -> new ApplicationBusinessException("Loan not found","NOT_FOUND"));
        LendingRequest lendingRequest = lendingRequestRepositoryPort.findByRequestId(loan.getRequestId())
                .orElseThrow(() -> new ApplicationBusinessException("Lending request not found","NOT_FOUND"));
        User lender = userRepositoryPort.findByUserId(lendingRequest.getRequestedTo())
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));
        User borrower = userRepositoryPort.findByUserId(lendingRequest.getRequestedBy())
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));
        return new LoanCaseOutputModel(
                loanCase.getCaseId(),
                loanCase.getLoanId(),
                borrower.getUserId(),
                borrower.getName(),
                lender.getName(),
                loan.getAmount(),
                loanCase.getReason(),
                loanCase.getFiledAt(),
                loanCase.getStatus().name(),
                loanCase.getAdminNotes(),
                loanCase.getClosedAt(),
                loanCase.getUpdatedAt()
        );
    }
}
