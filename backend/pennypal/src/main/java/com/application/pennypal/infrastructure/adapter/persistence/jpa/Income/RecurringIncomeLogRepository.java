package com.application.pennypal.infrastructure.adapter.persistence.jpa.Income;

import com.application.pennypal.domain.entity.RecurringIncomeLog;
import com.application.pennypal.domain.valueObject.RecurringIncomeLogStatus;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.IncomeEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.RecurringIncomeLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecurringIncomeLogRepository extends JpaRepository<RecurringIncomeLogEntity,Long> {
    boolean existsByIncomeIdAndDate(Long incomeId, LocalDate date);
    @Query("""
        SELECT
        COALESCE(SUM(i.amount), 0) AS totalAmount,
        COUNT(i) AS count
            FROM RecurringIncomeLogEntity i
            WHERE i.user.id = :userId
                AND i.status = 'PENDING'
                AND i.deleted = false
                AND i.date BETWEEN :startDate AND :endDate
    """)
    PendingIncomeStatsProjection getPendingRecurringIncomeStats(
                @Param("userId") Long userId,
                @Param("startDate") LocalDate startDate,
                @Param("endDate") LocalDate endDate
        );

    @Query("""
        SELECT r
        FROM RecurringIncomeLogEntity r
        WHERE r.user.id = :userId
          AND r.deleted = false
          AND r.status = :status
          AND r.date <= :date
    """)
    List<RecurringIncomeLogEntity> findPendingIncomesByUserAndDate(
            @Param("userId") Long userId,
            @Param("status") RecurringIncomeLogStatus status,
            @Param("date") LocalDate date
    );

    @Query("SELECT r FROM RecurringIncomeLogEntity r WHERE r.user.id = :userId AND r.income.id = :incomeId AND r.date = :date")
    Optional<RecurringIncomeLogEntity> findOneLog(
            @Param("userId") Long userId,
            @Param("incomeId") Long incomeId,
            @Param("date") LocalDate date
    );

}
