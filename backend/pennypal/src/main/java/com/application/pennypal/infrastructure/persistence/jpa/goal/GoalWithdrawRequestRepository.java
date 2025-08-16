package com.application.pennypal.infrastructure.persistence.jpa.goal;

import com.application.pennypal.domain.goal.entity.GoalWithdraw;
import com.application.pennypal.domain.valueObject.GoalWithdrawRequestStatus;
import com.application.pennypal.infrastructure.persistence.jpa.entity.GoalWithdrawRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GoalWithdrawRequestRepository extends JpaRepository<GoalWithdrawRequestEntity,Long> {
    @Query("SELECT COUNT(w) FROM GoalWithdrawRequestEntity w WHERE w.status = :status")
    long countByStatus(@Param("status") GoalWithdrawRequestStatus status);
    Optional<GoalWithdrawRequestEntity> findByWithdrawId(String withdrawId);

    List<GoalWithdrawRequestEntity> findAllByStatus(GoalWithdrawRequestStatus goalWithdrawRequestStatus);
}
