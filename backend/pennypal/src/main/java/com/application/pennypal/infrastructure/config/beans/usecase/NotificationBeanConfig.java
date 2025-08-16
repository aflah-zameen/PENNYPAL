package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.port.in.notification.GetAllAdminNotifications;
import com.application.pennypal.application.port.in.notification.GetAllUserNotification;
import com.application.pennypal.application.port.in.notification.MarkNotificationAsRead;
import com.application.pennypal.application.port.out.repository.NotificationRepositoryPort;
import com.application.pennypal.application.service.notification.GetAllUserNotificationService;
import com.application.pennypal.application.service.notification.MarkNotificationAsReadService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationBeanConfig {
    @Bean
    public MarkNotificationAsRead  markNotificationAsRead(NotificationRepositoryPort notificationRepositoryPort){
        return new MarkNotificationAsReadService(notificationRepositoryPort);
    }

    @Bean
    public GetAllUserNotification getAllUserNotification(NotificationRepositoryPort notificationRepositoryPort){
        return new GetAllUserNotificationService(notificationRepositoryPort);
    }

    @Bean
    public GetAllAdminNotifications getAllAdminNotifications(NotificationRepositoryPort notificationRepositoryPort) {
        return new com.application.pennypal.application.service.notification.GetAllAdminNotificationService(notificationRepositoryPort);
    }
}
