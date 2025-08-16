package com.application.pennypal.application.dto.output.notification;

import com.application.pennypal.domain.notification.NotificationType;

import java.time.LocalDateTime;

public record NotificationOutputModel(
        String id,
        String message,
        boolean read,
        LocalDateTime timeStamp,
        NotificationType type,
        String actionURL) {
}
