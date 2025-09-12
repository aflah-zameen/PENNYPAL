package com.application.pennypal.application.dto.output.notification;

import com.application.pennypal.domain.notification.NotificationType;

import java.time.Instant;
import java.time.LocalDateTime;

public record NotificationOutputModel(
        String id,
        String message,
        boolean read,
        Instant timeStamp,
        NotificationType type,
        String actionURL) {
}
