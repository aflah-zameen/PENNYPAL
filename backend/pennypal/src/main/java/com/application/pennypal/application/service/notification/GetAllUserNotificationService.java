package com.application.pennypal.application.service.notification;

import com.application.pennypal.application.dto.output.notification.NotificationOutputModel;
import com.application.pennypal.application.port.in.notification.GetAllUserNotification;
import com.application.pennypal.application.port.out.repository.NotificationRepositoryPort;
import com.application.pennypal.domain.notification.Notification;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllUserNotificationService implements GetAllUserNotification {
    private final NotificationRepositoryPort notificationRepositoryPort;
    @Override
    public List<NotificationOutputModel> execute(String userId) {
        List<Notification> notificationList = notificationRepositoryPort.getAllNotificationByUserId(userId);
        return notificationList.stream()
                .map(notification -> new NotificationOutputModel(
                        notification.getId(),
                        notification.getMessage(),
                        notification.isRead(),
                        notification.getTimeStamp(),
                        notification.getType(),
                        notification.getActionURL()
                ))
                .toList();
    }
}
