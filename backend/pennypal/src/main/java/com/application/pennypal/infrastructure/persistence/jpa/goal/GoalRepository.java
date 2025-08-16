package com.application.pennypal.infrastructure.persistence.jpa.goal;

import com.application.pennypal.domain.valueObject.GoalStatus;
import com.application.pennypal.infrastructure.persistence.jpa.entity.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<GoalEntity,Long>, JpaSpecificationExecutor<GoalEntity> {
    List<GoalEntity> findAllByUserIdAndDeletedFalseOrderByCreatedAtDesc(Long userId);

    long countByUserIdAndStatusAndDeletedFalse(Long userId, GoalStatus status);

    @Query("SELECT COALESCE(SUM(g.currentAmount), 0) FROM GoalEntity g WHERE g.user.userId = :userId AND g.deleted = false")
    BigDecimal getTotalSavedByUserId(String userId);


    Optional<GoalEntity> findByGoalId(String goalId);

    List<GoalEntity> findAllByUser_UserIdAndDeletedFalseOrderByCreatedAtDesc(String userId);

    Long countByUser_UserIdAndStatusAndDeletedFalse(String userId, GoalStatus goalStatus);

    @Query("""
    SELECT
            COUNT(g),
            SUM(CASE WHEN g.status = 'ACTIVE' THEN 1 ELSE 0 END),
            SUM(CASE WHEN g.status = 'COMPLETED' THEN 1 ELSE 0 END),
            SUM(g.currentAmount)
        FROM GoalEntity g
        WHERE g.deleted = false
    """)
    Object[] fetchGoalStats();
}
