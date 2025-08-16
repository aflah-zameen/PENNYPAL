package com.application.pennypal.infrastructure.persistence.jpa.entity;

import com.application.pennypal.domain.notification.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,updatable = false,unique = true)
    private String notificationId;

    private String userId;
    @Column(nullable = false)
    private String message;
    @Setter
    private boolean read;
    private LocalDateTime timeStamp;
    private NotificationType type;
    private String actionURL;
    private boolean forAdmin;

    public NotificationEntity(String notificationId,
                              String userId,
                              String message,
                              boolean read,
                              LocalDateTime timeStamp,
                              NotificationType type,
                              String actionURL,
                              boolean forAdmin){
        this.notificationId = notificationId;
        this.userId =userId;
        this.message = message;
        this.read = read;
        this.timeStamp = timeStamp;
        this.type = type;
        this.actionURL = actionURL;
        this.forAdmin = forAdmin;
    }

}
