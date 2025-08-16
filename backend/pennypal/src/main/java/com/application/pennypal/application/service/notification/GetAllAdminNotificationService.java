package com.application.pennypal.application.service.notification;

import com.application.pennypal.application.dto.output.notification.NotificationOutputModel;
import com.application.pennypal.application.port.in.notification.GetAllAdminNotifications;
import com.application.pennypal.application.port.out.repository.NotificationRepositoryPort;
import com.application.pennypal.domain.notification.Notification;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllAdminNotificationService implements GetAllAdminNotifications {
    private final NotificationRepositoryPort notificationRepositoryPort;
    @Override
    public List<NotificationOutputModel> execute() {
        List<Notification> notificationList = notificationRepositoryPort.getAllAdminNotifications();
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
