package com.application.pennypal.application.service.lent;

import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.in.admin.SuspendUser;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.domain.notification.Notification;
import com.application.pennypal.domain.notification.NotificationType;
import com.application.pennypal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SuspendUserService implements SuspendUser {
    private  final UserRepositoryPort userRepositoryPort;
    private final MessageBrokerPort messageBrokerPort;
    @Override
    public void execute(String userId) {
        User user = userRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));

        user = user.markSuspended();
        userRepositoryPort.update(user);

        String message = String.format(
                "Dear %s,\n\n" +
                        "Your account has been suspended by the admin due to violation of our policies. " +
                        "You will not be able to access PennyPal services until further notice. " +
                        "If you believe this is a mistake, please contact our support team.\n\n" +
                        "Regards,\nPennyPal Admin Team",
                user.getName()
        );

        Notification notification = Notification.create(
                user.getUserId(),
                message,
                NotificationType.USER_SUSPENSION,
                null,
                false
        );
        messageBrokerPort.notifyPrivateUser(notification,user.getUserId());
    }
}
