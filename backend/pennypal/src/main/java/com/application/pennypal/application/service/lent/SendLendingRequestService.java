package com.application.pennypal.application.service.lent;

import com.application.pennypal.application.dto.input.lend.LendRequestInputModel;
import com.application.pennypal.application.dto.output.lend.LendingRequestOutputModel;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.in.lent.SendLendingRequest;
import com.application.pennypal.application.port.out.repository.LendingRequestRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.domain.lend.LendingRequest;
import com.application.pennypal.domain.notification.Notification;
import com.application.pennypal.domain.notification.NotificationType;
import com.application.pennypal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendLendingRequestService implements SendLendingRequest {
    private final LendingRequestRepositoryPort lendingRequestRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final MessageBrokerPort messageBrokerPort;
    @Override
    public LendingRequestOutputModel execute(String userId, LendRequestInputModel inputModel) {
        LendingRequest lendingRequest = LendingRequest.create(
                userId,
                inputModel.requestTo(),
                inputModel.amount(),
                inputModel.message(),
                inputModel.proposedDeadline(),
                null
        );
        lendingRequest = lendingRequestRepositoryPort.save(lendingRequest);

        /// Fetch user details
        User requestBy = userRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));
        User requestTo = userRepositoryPort.findByUserId(inputModel.requestTo())
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));


        var message = requestBy.getName()+" requested $"+inputModel.amount().toString();
        Notification notification = Notification.create(
                inputModel.requestTo(),
                message,
                NotificationType.LENDING,
                null,
                false
        );

        /// Notify the user whom to requested
        messageBrokerPort.notifyPrivateUser(notification,inputModel.requestTo());

        return new LendingRequestOutputModel(
                lendingRequest.getRequestId(),
                lendingRequest.getRequestedBy(),
                requestBy.getName(),
                lendingRequest.getRequestedTo(),
                requestTo.getName(),
                lendingRequest.getAmount(),
                lendingRequest.getMessage(),
                lendingRequest.getProposedDeadline(),
                lendingRequest.getAcceptedDeadline(),
                lendingRequest.getStatus().getValue(),
                lendingRequest.getCreatedAt(),
                lendingRequest.getUpdatedAt()
        );

    }
}
