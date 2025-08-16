package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.domain.notification.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepositoryPort {

    Notification save(Notification notification);
    void update(Notification notification);
    Optional<Notification> getNotificationById(String notificationId);
    List<Notification> getAllNotificationByUserId(String userId);

    List<Notification> getAllAdminNotifications();
}
