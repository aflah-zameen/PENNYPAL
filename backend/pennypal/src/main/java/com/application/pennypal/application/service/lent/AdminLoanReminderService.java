package com.application.pennypal.application.service.lent;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.in.lent.AdminLoanReminder;
import com.application.pennypal.application.port.out.repository.LendingRequestRepositoryPort;
import com.application.pennypal.application.port.out.repository.LoanRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.domain.lend.LendingRequest;
import com.application.pennypal.domain.lend.Loan;
import com.application.pennypal.domain.notification.Notification;
import com.application.pennypal.domain.notification.NotificationType;
import com.application.pennypal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminLoanReminderService implements AdminLoanReminder {
    private final LoanRepositoryPort loanRepositoryPort;
    private final LendingRequestRepositoryPort lendingRequestRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final MessageBrokerPort messageBrokerPort;
    @Override
    public void execute(String loanId) {
        Loan loan = loanRepositoryPort.getLoan(loanId)
                .orElseThrow(() -> new ApplicationBusinessException("Loan not found","NOT_FOUND"));
        LendingRequest lendingRequest = lendingRequestRepositoryPort.findByRequestId(loan.getRequestId())
                .orElseThrow(() -> new ApplicationBusinessException("Lending request not found","NOT_FOUND"));

        User borrower = userRepositoryPort.findByUserId(lendingRequest.getRequestedBy())
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));

        String message = String.format(
                "Dear %s,\n\n" +
                        "This is an urgent reminder regarding your pending loan repayment:\n" +
                        "• Loan ID: %s\n" +
                        "• Loan Amount: ₹%.2f\n" +
                        "• Due Date: %s\n\n" +
                        "The lender has already filed a case against you due to non-repayment. " +
                        "If the repayment is not completed within 3 days, your account will be suspended.\n\n" +
                        "We strongly urge you to take immediate action to avoid further consequences.\n\n" +
                        "Regards,\nAdmin Team",
                borrower.getName(),
                loan.getLoanId(),
                loan.getAmount(),
                loan.getDeadline()
        );

        Notification notification = Notification.create(
                borrower.getUserId(),
                message,
                NotificationType.LOAN_CASE,
                null,
                false
        );

        messageBrokerPort.notifyPrivateUser(notification, borrower.getUserId());

    }
}
