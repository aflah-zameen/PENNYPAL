package com.application.pennypal.application.service.lent;

import com.application.pennypal.application.dto.input.lend.LoanCaseInputModel;
import com.application.pennypal.application.dto.output.lend.LoanOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.mappers.lent.LentApplicationMapper;
import com.application.pennypal.application.port.in.lent.FileLoanCase;
import com.application.pennypal.application.port.out.repository.*;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.domain.LoanCase.LoanCase;
import com.application.pennypal.domain.lend.LendingRequest;
import com.application.pennypal.domain.lend.Loan;
import com.application.pennypal.domain.notification.Notification;
import com.application.pennypal.domain.notification.NotificationType;
import com.application.pennypal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class FileLoanCaseService implements FileLoanCase {
    private final LoanCaseRepositoryPort caseRepositoryPort;
    private final LoanRepositoryPort loanRepositoryPort;
    private final LendingRequestRepositoryPort lendingRequestRepositoryPort;
    private final MessageBrokerPort messageBrokerPort;
    private final UserRepositoryPort userRepositoryPort;
    private final LentApplicationMapper lentApplicationMapper;
    @Override
    public LoanOutputModel execute(String userId, LoanCaseInputModel inputModel) {
        Loan loan = loanRepositoryPort.getLoan(inputModel.loanId())
                .orElseThrow(() -> new ApplicationBusinessException("Loan cannot be found","NOT_FOUND"));
        LendingRequest lendingRequest = lendingRequestRepositoryPort.findByRequestId(loan.getRequestId())
                .orElseThrow(() -> new ApplicationBusinessException("Lending request cannot be found","NOT_FOUND"));
        User borrower = userRepositoryPort.findByUserId(lendingRequest.getRequestedBy())
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));
        User lender = userRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));
        LoanCase loanCase = LoanCase.create(
                inputModel.loanId(),
                userId,
                inputModel.reason(),
                LocalDateTime.now()
        );
        loanCase = caseRepositoryPort.save(loanCase);

        String caseFiledReminder =String.format(
                """
                        Dear %s,
                        
                        A case has been registered against you for failing to repay the loan of ₹%.2f, which was due on %s.
                        The case was filed by %s and will now be reviewed by the PennyPal admin team.
                        Further action may be taken based on the outcome of this review, including suspension or account restrictions.
                        
                        Please resolve the pending repayment as soon as possible to avoid escalation.
                        
                        — PennyPal Compliance Team""",
                borrower.getName(),
                loan.getAmount(),
                formatDate(loan.getDeadline()),
                lender.getName()
        );
        String caseFiledConfirmation = String.format(
                """
                        Dear %s,
                        
                        Your case regarding the unpaid loan of ₹%.2f (due on %s) has been successfully filed against %s.
                        The PennyPal admin team will now review the case and take appropriate action based on the findings.
                        You will be notified once a decision has been made or if further information is required.
                        
                        Thank you for helping us maintain trust and accountability within the platform.
                        
                        — PennyPal Compliance Team""",
                lender.getName(),
                loan.getAmount(),
                formatDate(loan.getDeadline()),
                borrower.getName()
        );

        String adminCaseNotification = String.format(
                """
                        Admin Alert:
                        
                        A new case has been filed by %s against %s for non-payment of a loan amounting to ₹%.2f.
                        The loan was due on %s and remains unpaid.
                        
                        Please review the case details and take appropriate action based on platform policies.
                        Ensure that both parties are notified once a decision is made or if further clarification is needed.
                        
                        — PennyPal System Notification""",
                lender.getName(),
                borrower.getName(),
                loan.getAmount(),
                formatDate(loan.getDeadline())
        );




        Notification borrowerNotif = Notification.create(
                borrower.getUserId(),
                caseFiledReminder,
                NotificationType.LOAN_CASE,
                null,
                false
        );
        Notification lenderNotif = Notification.create(
                lender.getUserId(),
                caseFiledConfirmation,
                NotificationType.LOAN_CASE,
                null,
                false
        );
        Notification adminNotif = Notification.create(
                null,
                adminCaseNotification,
                NotificationType.LOAN_CASE,
                null,
                true
        );

        messageBrokerPort.notifyPrivateUser(borrowerNotif, borrower.getUserId());
        messageBrokerPort.notifyPrivateUser(lenderNotif, lender.getUserId());
        messageBrokerPort.publishLoanCase(adminNotif,loanCase);
        loan = loan.markCaseFiled();
        loanRepositoryPort.update(loan);
       return lentApplicationMapper.toOutput(loan);
    }
    private String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

}
