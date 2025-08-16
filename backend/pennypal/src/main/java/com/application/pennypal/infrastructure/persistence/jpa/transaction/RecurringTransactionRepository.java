package com.application.pennypal.infrastructure.persistence.jpa.transaction;

import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import com.application.pennypal.domain.valueObject.TransactionType;
import com.application.pennypal.infrastructure.persistence.jpa.entity.RecurringTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecurringTransactionRepository extends JpaRepository<RecurringTransactionEntity,Long> {
    @Query("""
        SELECT COUNT(i)
        FROM  RecurringTransactionEntity i
        WHERE i.user.userId = :userId
          AND i.transactionType = :transactionType
          AND i.active = true
          AND i.deleted = false
    """)
    Integer countActiveRecurringTransactionByUserId(@Param("userId") String userId,
                                                    @Param("transactionType") TransactionType transactionType);

    List<RecurringTransactionEntity> findAllByActiveTrueAndDeletedFalse();

    Optional<RecurringTransactionEntity> findByUser_UserIdAndRecurringId(String userId, String recurringId);

    List<RecurringTransactionEntity> findAllByUser_UserIdAndTransactionTypeAndDeletedFalse(String userId, TransactionType transactionType);

    Optional<RecurringTransactionEntity> findByRecurringId(String recurringId);
}
