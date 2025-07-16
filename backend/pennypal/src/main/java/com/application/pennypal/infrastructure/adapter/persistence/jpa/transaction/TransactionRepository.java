package com.application.pennypal.infrastructure.adapter.persistence.jpa.transaction;

import com.application.pennypal.domain.entity.Transactions;
import com.application.pennypal.domain.valueObject.TransactionOriginType;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.TransactionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {

    @Query(value = """
        SELECT COALESCE(SUM(t.amount), 0)
        FROM TransactionEntity t
        WHERE t.type = 'INCOME'
          AND t.status = 'COMPLETED'
          AND t.transactionDate BETWEEN :start AND :end
    """)
    BigDecimal getTotalAmountForMonth(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    boolean existsByUserIdAndAmountAndTransactionDateAndTypeAndOriginIdAndCategoryId(
            Long userId,
            BigDecimal amount,
            LocalDate transactionDate,
            TransactionOriginType type,
            Long originId,
            Long categoryId
    );

    @Query(value = """
            SELECT t FROM TransactionEntity t
            WHERE t.user.id = :userId
            AND t.type = :originType
            ORDER BY t.createdAt DESC
            """)
    List<TransactionEntity> findRecentIncomeTransactions(
            @Param("userId") Long userId,
            @Param("originType") TransactionOriginType originType,
            Pageable pageable
    );

    List<TransactionEntity> findAllByUserIdAndOriginIdAndTypeOrderByCreatedAtDesc(Long userId, Long originId,TransactionOriginType type);
}
