package com.application.pennypal.application.service.lent;

import com.application.pennypal.application.dto.output.lend.LoanOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.mappers.lent.LentApplicationMapper;
import com.application.pennypal.application.port.in.lent.RemindLoanPayment;
import com.application.pennypal.application.port.out.repository.LendingRequestRepositoryPort;
import com.application.pennypal.application.port.out.repository.LoanRepositoryPort;
import com.application.pennypal.application.port.out.repository.NotificationRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.domain.lend.LendingRequest;
import com.application.pennypal.domain.lend.Loan;
import com.application.pennypal.domain.notification.Notification;
import com.application.pennypal.domain.notification.NotificationType;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class RemindLoanPaymentService implements RemindLoanPayment {
    private final LoanRepositoryPort loanRepositoryPort;
    private final MessageBrokerPort messageBrokerPort;
    private final UserRepositoryPort userRepositoryPort;
    private final LendingRequestRepositoryPort lendingRequestRepositoryPort;
    private final LentApplicationMapper lentApplicationMapper;
    @Override
    public LoanOutputModel execute(UserDomainDTO lender, String loanId) {
        Loan loan = loanRepositoryPort.getLoan(loanId)
                .orElseThrow(() -> new ApplicationBusinessException("Loan cannot be found","NOT_FOUND"));
        LendingRequest lendingRequest = lendingRequestRepositoryPort.findByRequestId(loan.getRequestId())
                .orElseThrow(() -> new ApplicationBusinessException("Lent request cannot be found","NOT_FOUND"));

        User borrower = userRepositoryPort.findByUserId(lendingRequest.getRequestedBy())
                .orElseThrow(() -> new UserNotFoundApplicationException("USer not found"));

        String message = String.format(
                "Hi %s,\n" +
                        "Just a friendly reminder that the repayment for the loan of ₹%.2f you received on %s is still pending.\n" +
                        "The agreed deadline is %s.\n" +
                        "Please make the payment at your earliest convenience to avoid further action.\n\n" +
                        "Thank you for your cooperation,\n" +
                        "— %s",
                borrower.getName(),
                loan.getAmount(),
                formatDate(loan.getAcceptedAt()),
                formatDate(loan.getDeadline()),
                lender.userName()
        );


        Notification notification = Notification.create(
                lendingRequest.getRequestedBy(),
                message,
                NotificationType.LENDING,
                null,
                false
                );
        /// update loan state
        loan = loan.markReminded();
        loan = loanRepositoryPort.update(loan);

        /// notify the user
        messageBrokerPort.notifyPrivateUser(notification,borrower.getUserId());

        return lentApplicationMapper.toOutput(loan);
    }
    private String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return dateTime.format(formatter);
    }


}
