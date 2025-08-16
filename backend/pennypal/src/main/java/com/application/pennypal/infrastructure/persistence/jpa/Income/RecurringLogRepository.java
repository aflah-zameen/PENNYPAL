package com.application.pennypal.infrastructure.persistence.jpa.Income;

import com.application.pennypal.domain.transaction.entity.RecurringTransactionLog;
import com.application.pennypal.domain.valueObject.RecurringLogStatus;
import com.application.pennypal.domain.valueObject.TransactionType;
import com.application.pennypal.infrastructure.persistence.jpa.entity.RecurringTransactionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecurringLogRepository extends JpaRepository<RecurringTransactionLogEntity,Long> {
    @Query("""
        SELECT
        COALESCE(SUM(i.amount), 0) AS totalAmount,
        COUNT(i) AS count
            FROM RecurringTransactionLogEntity i
            WHERE i.user.userId = :userId
                AND i.status = 'PENDING'
                AND i.transactionType = :transactionType
                AND i.deleted = false
                AND i.dateFor BETWEEN :startDate AND :endDate
    """)
    PendingTransactionStatsProjection getPendingRecurringTransactionStats(
                @Param("userId") String userId,
                @Param("transactionType") TransactionType transactionType,
                @Param("startDate") LocalDate startDate,
                @Param("endDate") LocalDate endDate
        );

    @Query("""
        SELECT r
        FROM RecurringTransactionLogEntity r
        WHERE r.user.id = :userId
          AND r.deleted = false
          AND r.status = :status
          AND r.dateFor <= :date
    """)
    List<RecurringTransactionLogEntity> findPendingIncomesByUserAndDate(
            @Param("userId") Long userId,
            @Param("status") RecurringLogStatus status,
            @Param("date") LocalDate date
    );

    @Query("SELECT r FROM RecurringTransactionLogEntity r WHERE r.user.userId = :userId AND r.transactionType = :transactionType AND r.transactionId = :transactionId AND r.dateFor = :date")
    Optional<RecurringTransactionLogEntity> findOneLog(
            @Param("userId") String userId,
            @Param("transactionId") String transactionId,
            @Param("date") LocalDate date,
            @Param("transactionType") TransactionType transactionType
    );

    Optional<RecurringTransactionLogEntity> findByTransactionId(String transactionId);

    boolean existsByRecurringIdAndDateFor(String recurringId, LocalDate dateFor);

    Optional<RecurringTransactionLogEntity> findByTransactionIdAndUser_UserId(String recurringLogId, String userId);

    List<RecurringTransactionLogEntity> findAllByUser_UserIdAndTransactionTypeAndStatus(String userId, TransactionType transactionType, RecurringLogStatus recurringLogStatus);
}
