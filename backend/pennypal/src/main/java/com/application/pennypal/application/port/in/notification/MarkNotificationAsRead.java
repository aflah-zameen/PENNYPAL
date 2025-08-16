package com.application.pennypal.application.port.in.notification;

public interface MarkNotificationAsRead {
    void execute(String userId,String notificationId);
}
