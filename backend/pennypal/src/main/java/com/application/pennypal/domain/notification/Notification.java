package com.application.pennypal.domain.notification;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Notification {
    private final String id;
    private final String userId;
    private final String message;
    private boolean read;
    private final LocalDateTime timeStamp;
    private final NotificationType type;
    private final String actionURL;
    private final boolean forAdmin;

    private Notification(
            String id,
            String userId,
            String message,
            boolean read,
            LocalDateTime timeStamp,
            NotificationType type,
            String actionURL,
            boolean forAdmin
    ){
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.read = read;
        this.timeStamp = timeStamp;
        this.type = type;
        this.actionURL = actionURL;
        this.forAdmin = forAdmin;
    }

    public static Notification create(
            String userId,
            String message,
            NotificationType type,
            String actionURL,
            boolean forAdmin
    ){
        String id = "NOTIF_"+ UUID.randomUUID();
        return new Notification(
                id,
                userId,
                message,
                false,
                LocalDateTime.now(),
                type,
                actionURL,
                forAdmin
        );
    }

    public static Notification reconstruct(
            String id,
            String userId,
            String message,
            boolean read,
            LocalDateTime timeStamp,
            NotificationType type,
            String actionURL,
            boolean forAdmin
    ){
        return new Notification(
                id,
                userId,
                message,
                read,
                timeStamp,
                type,
                actionURL,
                forAdmin
        );
    }

    public Notification markAsRead(){
        this.read = true;
        return this;
    }

}
