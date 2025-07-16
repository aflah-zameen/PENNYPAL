package com.application.pennypal.infrastructure.adapter.persistence.jpa.goal;

import com.application.pennypal.domain.valueObject.GoalStatus;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface GoalRepository extends JpaRepository<GoalEntity,Long> {
    List<GoalEntity> findAllByUserIdAndDeletedFalseOrderByCreatedAtDesc(Long userId);

    long countByUserIdAndStatusAndDeletedFalse(Long userId, GoalStatus status);

    @Query("SELECT COALESCE(SUM(g.currentAmount), 0) FROM GoalEntity g WHERE g.user.id = :userId AND g.deleted = false")
    BigDecimal getTotalSavedByUserId(Long userId);


}
