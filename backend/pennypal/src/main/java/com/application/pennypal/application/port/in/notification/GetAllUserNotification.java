package com.application.pennypal.application.port.in.notification;

import com.application.pennypal.application.dto.output.notification.NotificationOutputModel;

import java.util.List;

public interface GetAllUserNotification {
    List<NotificationOutputModel> execute(String userId);
}
