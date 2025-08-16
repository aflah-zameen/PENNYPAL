package com.application.pennypal.application.service.notification;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.notification.MarkNotificationAsRead;
import com.application.pennypal.application.port.out.repository.NotificationRepositoryPort;
import com.application.pennypal.domain.notification.Notification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MarkNotificationAsReadService implements MarkNotificationAsRead {
    private final NotificationRepositoryPort notificationRepositoryPort;
    @Override
    public void execute(String userId, String notificationId) {
        Notification notification = notificationRepositoryPort.getNotificationById(notificationId)
                .orElseThrow(() -> new ApplicationBusinessException("Notification cannot be found","NOT_FOUND"));

        /// Mark as read
        notification = notification.markAsRead();
        notificationRepositoryPort.update(notification);
    }
}
