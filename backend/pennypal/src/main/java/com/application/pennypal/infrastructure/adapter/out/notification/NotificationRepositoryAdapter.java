package com.application.pennypal.infrastructure.adapter.out.notification;

import com.application.pennypal.application.port.out.repository.NotificationRepositoryPort;
import com.application.pennypal.domain.notification.Notification;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.entity.NotificationEntity;
import com.application.pennypal.infrastructure.persistence.jpa.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepositoryPort {
    private final NotificationRepository notificationRepository;

    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = notificationRepository.save(new NotificationEntity(
                notification.getId(),
                notification.getUserId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getTimeStamp(),
                notification.getType(),
                notification.getActionURL(),
                notification.isForAdmin()
        ));
        return Notification.reconstruct(
                entity.getNotificationId(),
                entity.getUserId(),
                entity.getMessage(),
                entity.isRead(),
                entity.getTimeStamp(),
                entity.getType(),
                entity.getActionURL(),
                entity.isForAdmin()
        );

    }

    @Override
    public void update(Notification notification) {
        NotificationEntity entity = notificationRepository.findByNotificationId(notification.getId())
                .orElseThrow(() -> new InfrastructureException("Notification jpa entry not found","NOT_FOUND"));
        entity.setRead(notification.isRead());
        notificationRepository.save(entity);
    }

    @Override
    public Optional<Notification> getNotificationById(String notificationId) {
        return notificationRepository.findByNotificationId(notificationId).map(entity ->
            Notification.reconstruct(
                    entity.getNotificationId(),
                    entity.getUserId(),
                    entity.getMessage(),
                    entity.isRead(),
                    entity.getTimeStamp(),
                    entity.getType(),
                    entity.getActionURL(),
                    entity.isForAdmin()
            ));
    }

    @Override
    public List<Notification> getAllNotificationByUserId(String userId) {
        Sort sort = Sort.by(Sort.Direction.DESC,"timeStamp");
        return notificationRepository.findAllByUserIdAndReadFalse(userId,sort).stream()
                .map(entity -> Notification.reconstruct(
                        entity.getNotificationId(),
                        entity.getUserId(),
                        entity.getMessage(),
                        entity.isRead(),
                        entity.getTimeStamp(),
                        entity.getType(),
                        entity.getActionURL(),
                        entity.isForAdmin()
                ))
                .toList();
    }

    @Override
    public List<Notification> getAllAdminNotifications() {
        Sort sort = Sort.by(Sort.Direction.DESC, "timeStamp");
        return notificationRepository.findAllByForAdminTrueAndReadFalse(sort).stream()
                .map(entity -> Notification.reconstruct(
                        entity.getNotificationId(),
                        entity.getUserId(),
                        entity.getMessage(),
                        entity.isRead(),
                        entity.getTimeStamp(),
                        entity.getType(),
                        entity.getActionURL(),
                        entity.isForAdmin()
                ))
                .toList();
    }
}
