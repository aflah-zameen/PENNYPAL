package com.application.pennypal.infrastructure.persistence.jpa.Income;

import com.application.pennypal.domain.valueObject.RecurringStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//public interface IncomeRepository extends JpaRepository<IncomeEntity,Long>, JpaSpecificationExecutor<IncomeEntity> {
//
//    List<IncomeEntity> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
//
//    List<IncomeEntity> findAllByUserIdAndIsRecurringTrueAndDeletedFalseOrderByIncomeDateDesc(Long userId);
//
//    List<IncomeEntity> findAllByIsRecurringTrueAndRecurrenceActiveTrueAndDeletedFalse();
//    ;
//
//
//    @Query("""
//        SELECT
//          COALESCE(SUM(i.amount), 0) AS totalAmount,
//          COUNT(i) AS count
//            FROM IncomeEntity i
//            WHERE i.user.id = :userId
//              AND i.status = 'PENDING'
//              AND i.isRecurring = false
//              AND i.deleted = false
//              AND i.incomeDate BETWEEN :startDate AND :endDate
//        """)
//        PendingIncomeStatsProjection getPendingOneTimeIncomeStats(
//                @Param("userId") Long userId,
//                @Param("startDate") LocalDate startDate,
//                @Param("endDate") LocalDate endDate
//        );
//
//    @Query("""
//        SELECT COUNT(i)
//        FROM IncomeEntity i
//        WHERE i.user.id = :userId
//          AND i.isRecurring = true
//          AND i.recurrenceActive = true
//          AND i.deleted = false
//    """)
//    int countActiveRecurringIncomeByUserId(@Param("userId") Long userId);
//
//    @Query("""
//        SELECT COUNT(i) > 0
//        FROM IncomeEntity i
//        WHERE i.user.id = :userId
//          AND i.amount = :amount
//          AND i.title = :title
//          AND i.incomeDate = :incomeDate
//          AND i.deleted = false
//    """)
//    boolean existsDuplicateIncome(
//            @Param("userId") Long userId,
//            @Param("amount") BigDecimal amount,
//            @Param("title") String title,
//            @Param("incomeDate") LocalDate incomeDate
//    );
//
//    @Query("SELECT i FROM IncomeEntity i WHERE i.user.id = :userId AND i.isRecurring = false AND i.incomeDate <= :date AND i.status = :status")
//    List<IncomeEntity> findPendingNonRecurringIncomeBeforeDate(
//            @Param("userId") Long userId,
//            @Param("date") LocalDate date,
//            @Param("status") RecurringStatus status
//    );
//}
