package com.application.pennypal.infrastructure.adapter.out.lent;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.admin.SuspendUser;
import com.application.pennypal.application.port.in.lent.UpdateLoanCaseAction;
import com.application.pennypal.application.port.out.repository.LoanCaseRepositoryPort;
import com.application.pennypal.application.port.out.repository.LoanRepositoryPort;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.domain.LoanCase.CaseActionType;
import com.application.pennypal.domain.LoanCase.LoanCase;
import com.application.pennypal.domain.LoanCase.LoanCaseStatus;
import com.application.pennypal.domain.lend.Loan;
import com.application.pennypal.domain.notification.Notification;
import com.application.pennypal.domain.notification.NotificationType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCaseActionInfraService {
    private final SuspendUser suspendUser;
    private final UpdateLoanCaseAction updateLoanCaseAction;
    private final LoanCaseRepositoryPort loanCaseRepositoryPort;
    private final LoanRepositoryPort loanRepositoryPort;
    private final MessageBrokerPort messageBrokerPort;
    @Transactional
    public void caseAction(String userId, String caseId, CaseActionType actionType){
        if(actionType.equals(CaseActionType.SUSPEND_USER)){
            suspendUser.execute(userId);
            updateLoanCaseAction.execute(caseId, LoanCaseStatus.RESOLVED);
        }
        else if(actionType.equals(CaseActionType.CANCEL_CASE)){
            updateLoanCaseAction.execute(caseId,LoanCaseStatus.RESOLVED);
            LoanCase loanCase= loanCaseRepositoryPort.getLoanCase(caseId);
            Loan loan = loanRepositoryPort.getLoan(loanCase.getLoanId())
                    .orElseThrow(() -> new ApplicationBusinessException("Loan not found","NOT_FOUND"));

            String message = "The case filed on your Loan ID " + loan.getLoanId() + " has been cancelled by the admin.";

            Notification notification = Notification.create(
                    loanCase.getFiledBy(),
                    message,
                    NotificationType.LOAN_CASE,
                    null,
                    false
            );
            messageBrokerPort.notifyPrivateUser(notification, loanCase.getFiledBy());
        }
    }
}
