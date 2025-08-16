package com.application.pennypal.infrastructure.persistence.jpa.notification;

import com.application.pennypal.infrastructure.persistence.jpa.entity.NotificationEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<NotificationEntity,Long> {
    Optional<NotificationEntity> findByNotificationId(String notificationId);
    List<NotificationEntity> findAllByUserIdAndReadFalse(String userId, Sort sort);
    List<NotificationEntity> findAllByForAdminTrueAndReadFalse(Sort sort);
}
