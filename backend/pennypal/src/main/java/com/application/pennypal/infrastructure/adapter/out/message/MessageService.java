package com.application.pennypal.infrastructure.adapter.out.message;

import com.application.pennypal.application.dto.output.notification.NotificationOutputModel;
import com.application.pennypal.infrastructure.persistence.jpa.entity.NotificationEntity;
import com.application.pennypal.infrastructure.persistence.jpa.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final NotificationRepository notificationRepository;
    public List<NotificationOutputModel> getAllNotificationsForUser(String userId){
       List<NotificationEntity> notificationEntityList =  notificationRepository.findAllByUserId(userId);
         return notificationEntityList.stream().map(entity ->
                new NotificationOutputModel(
                          entity.getNotificationId(),
                          entity.getMessage(),
                          entity.isRead(),
                          entity.getTimeStamp().toInstant(ZoneOffset.UTC),
                          entity.getType(),
                          entity.getActionURL()
                )
         ).toList();
    }
}
