package com.application.pennypal.application.service.lend;

import com.application.pennypal.application.dto.input.lend.LendingRequestStatusUpdateInputModel;
import com.application.pennypal.application.dto.output.lend.LendingRequestStatusOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.out.repository.LendingRequestRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.domain.lend.LendingRequest;
import com.application.pennypal.domain.notification.Notification;
import com.application.pennypal.domain.notification.NotificationType;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.infrastructure.adapter.out.lend.UpdateLendingRequestStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateLendingRequestStatusService implements UpdateLendingRequestStatus {
    private final LendingRequestRepositoryPort lendingRequestRepositoryPort;
    private final MessageBrokerPort messageBrokerPort;
    private final UserRepositoryPort userRepositoryPort;
    @Override
    public LendingRequestStatusOutputModel execute(LendingRequestStatusUpdateInputModel inputModel) {
        LendingRequest lendingRequest = lendingRequestRepositoryPort.findByRequestId(inputModel.requestId())
                .orElseThrow(() -> new ApplicationBusinessException("Lending Request not found","NOT_FOUND"));
        lendingRequest = lendingRequest.updateStatus(inputModel.status());
        lendingRequest = lendingRequest.setAcceptedDeadline(inputModel.acceptedDeadline());
        LendingRequest updatedRequest = lendingRequestRepositoryPort.update(lendingRequest);


        User requestedToUser = userRepositoryPort.findByUserId(lendingRequest.getRequestedTo())
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));

        /// Notify user
        var message = switch (inputModel.status()){
            case ACCEPTED -> "Your lending amount : "+lendingRequest.getAmount().toString()+" request from "+requestedToUser.getName()+" accepted";
            case REJECTED -> "Your lending amount : "+lendingRequest.getAmount().toString()+" request from "+requestedToUser.getName()+" rejected";
            default -> null;
        };

        if(message != null){
            Notification notification = Notification.create(
                    lendingRequest.getRequestedBy(),
                    message,
                    NotificationType.LENDING,
                    null,
                    false
            );
            messageBrokerPort.notifyPrivateUser(notification, lendingRequest.getRequestedBy());
        }
        return new LendingRequestStatusOutputModel(
                updatedRequest.getStatus(),
                updatedRequest.getAcceptedDeadline()
        );
    }
}
