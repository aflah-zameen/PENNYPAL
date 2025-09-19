package com.application.pennypal.infrastructure.persistence.jpa.transaction;

import com.application.pennypal.application.dto.output.card.CardExpenseOverviewOutputModel;
import com.application.pennypal.application.dto.output.sale.PaymentStatusDTO;
import com.application.pennypal.application.dto.output.sale.SalesDataOutput;
import com.application.pennypal.domain.transaction.entity.Transaction;
import com.application.pennypal.domain.valueObject.PaymentMethod;
import com.application.pennypal.domain.valueObject.TransactionStatus;
import com.application.pennypal.domain.valueObject.TransactionType;
import com.application.pennypal.infrastructure.persistence.jpa.entity.TransactionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {

    @Query(value = """
        SELECT COALESCE(SUM(t.amount), 0)
        FROM TransactionEntity t
        WHERE t.transactionType = :transactionType
          AND t.user.userId = :userId
          AND t.status = 'COMPLETED'
          AND t.transactionDate BETWEEN :start AND :end
    """)
    BigDecimal getTotalAmountForMonth(
            @Param("userId") String userId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("transactionType") TransactionType transactionType
    );

//    boolean existsByUserIdAndAmountAndTransactionDateAndTypeAndTransactionIdAndCategoryId(
//            Long userId,
//            BigDecimal amount,
//            LocalDate transactionDate,
//            TransactionType type,
//            String originId,
//            Long categoryId
//    );

    @Query(value = """
            SELECT t FROM TransactionEntity t
            WHERE t.user.userId = :userId
            AND t.transactionType = :transactionType
            ORDER BY t.createdAt DESC
            """)
    List<TransactionEntity> findRecentIncomeTransactions(
            @Param("userId") String userId,
            @Param("transactionType") TransactionType transactionType,
            Pageable pageable
    );

//    List<TransactionEntity> findAllByUserIdAndOriginIdAndTypeOrderByCreatedAtDesc(Long userId, Long originId, TransactionType type);

    Optional<TransactionEntity> findByTransactionId(String transactionId);

    Optional<TransactionEntity> findByTransactionIdAndTransactionType(String incomeId, TransactionType transactionType);

    boolean existsByUser_UserIdAndIsFromRecurringTrueAndRecurringTransactionIdAndTransactionDateAndTransactionType(
            String userId, String recurringTransactionId,
            LocalDate transactionDate, TransactionType transactionType);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionEntity t WHERE t.card.cardId = :cardId AND t.transactionType = :transactionType")
    BigDecimal getTotalAmountByCardAndType(@Param("cardId") String cardId, @Param("transactionType") TransactionType transactionType);

    @Query(value = """
      SELECT MONTH(t.transactionDate) AS label, SUM(t.amount) AS total
      FROM TransactionEntity t
      WHERE t.card.cardId = :cardId AND t.transactionType = :transactionType
      GROUP BY MONTH(t.transactionDate)
      ORDER BY MONTH(t.transactionDate)
    """)
    List<SpendProjection> findMonthlySpending(@Param("cardId") String cardId, @Param("transactionType") TransactionType transactionType);

    @Query(value = """
      SELECT EXTRACT(DOW FROM t.transaction_date) AS label,
      SUM(t.amount) AS total
      FROM transactions t
      WHERE t.card_id = :cardId AND t.transaction_type = :transactionType
      GROUP BY label
      ORDER BY label
    """,nativeQuery = true)
    List<SpendProjection> findWeeklySpending(@Param("cardId") String cardId, @Param("transactionType") String transactionType);

    @Query(value = """
      SELECT YEAR(t.transactionDate) AS label, SUM(t.amount) AS total
      FROM TransactionEntity t
      WHERE t.card.cardId = :cardId AND t.transactionType = :transactionType
      GROUP BY YEAR(t.transactionDate)
      ORDER BY YEAR(t.transactionDate)
    """)
    List<SpendProjection> findYearlySpending(@Param("cardId") String cardId, @Param("transactionType") TransactionType transactionType);

    @Query("""
    SELECT c.categoryId, c.name, c.isActive, c.isDefault, c.sortOrder, c.color, c.icon, SUM(t.amount), 0.0
    FROM TransactionEntity t
    JOIN t.category c
    WHERE t.card.cardId = :cardId
      AND t.transactionType = 'EXPENSE'
      AND t.transactionDate BETWEEN :startDate AND :endDate
    GROUP BY c.categoryId, c.name, c.isActive, c.isDefault, c.sortOrder, c.color, c.icon
""")
    List<Object[]> getCardExpenseOverviewRaw(
            @Param("cardId") String cardId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );


    List<TransactionEntity> findAllByCard_CardId(String cardId, Pageable pageable);

    List<TransactionEntity> findAllByUser_UserIdAndTransactionType(String userId, TransactionType transactionType, Sort sort);

    @Query("SELECT t.category, SUM(t.amount) as totalSpent, COUNT(t) as transactionCount " +
            "FROM TransactionEntity t " +
            "WHERE t.user.userId = :userId AND t.transactionType = :transactionType " +
            "GROUP BY t.category " +
            "ORDER BY totalSpent DESC")
    List<Object[]> findTopCategoryByUserAndType(@Param("userId") String userId,
                                                @Param("transactionType") TransactionType transactionType);


    @Query("SELECT t.card, SUM(t.amount) as totalSpent " +
            "FROM TransactionEntity t " +
            "WHERE t.user.userId = :userId AND t.transactionType = :transactionType " +
            "GROUP BY t.card " +
            "ORDER BY totalSpent DESC")
    List<Object[]> findMostUsedCardAggregate(@Param("userId") String userId,
                                             @Param("transactionType") TransactionType transactionType);


    @Query("""
        SELECT t FROM TransactionEntity t
        WHERE t.transferToUserId = :userId OR t.transferFromUserId = :userId
        ORDER BY t.createdAt DESC
        """)
    List<TransactionEntity> findUserInvolvedTransactions(@Param("userId") String userId);

    @Query("""
    SELECT t.transactionDate,
           SUM(CASE WHEN t.transactionType = 'INCOME' THEN t.amount ELSE 0 END),
           SUM(CASE WHEN t.transactionType = 'EXPENSE' THEN t.amount ELSE 0 END)
    FROM TransactionEntity t
    WHERE t.transactionDate BETWEEN :startDate AND :endDate
    AND t.user.userId = :userId
    GROUP BY t.transactionDate
    ORDER BY t.transactionDate
""")
    List<Object[]> getRawDailyIncomeExpenseSummary(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("userId") String userId
    );

    @Query("""
    SELECT t.category.name, SUM(t.amount)
    FROM TransactionEntity t
    WHERE t.transactionType = 'EXPENSE'
      AND t.transactionDate BETWEEN :startDate AND :endDate
      AND t.user.userId = :userId
    GROUP BY t.category
    """)
    List<Object[]> getRawExpenseBreakdownBetweenDatesAndUser(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("userId") String userId
    );

    @Query("""
        SELECT COUNT(t), COALESCE(AVG(t.amount), 0)
        FROM TransactionEntity t
        WHERE t.transactionType = 'WALLET'
        AND t.user.userId = :userId
    """)
    Object fetchWalletStatsByUserId(@Param("userId") String userId);

    @Query("""
        SELECT t FROM TransactionEntity t
        WHERE t.card.cardId = :cardId OR t.receiverCardId = :cardId
        ORDER BY t.createdAt DESC
    """)
    List<TransactionEntity> findCardTransactions(String cardId, Pageable pageable);

    @Query("SELECT t FROM TransactionEntity t WHERE t.planId = :planId AND t.transactionDate BETWEEN :start AND :end")
    List<TransactionEntity> findByPlanIdAndDateRange(@Param("planId") String planId, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionEntity t " +
            "WHERE t.status = :status AND t.transactionDate BETWEEN :start AND :end AND transactionType = 'SUBSCRIPTION' ")
    BigDecimal sumAmountByStatusAndDateRange(@Param("status") TransactionStatus status,
                                             @Param("start") LocalDate start,
                                             @Param("end") LocalDate end);

    @Query(value = """
    SELECT TO_CHAR(t.transaction_date, 'YYYYMM') AS month,
           t.plan_id AS planId,
           SUM(t.amount) AS revenue,
           COUNT(*) AS transactionCount
    FROM transactions t
    WHERE t.transaction_date BETWEEN :start AND :end
    GROUP BY TO_CHAR(t.transaction_date, 'YYYYMM'), t.plan_id
""", nativeQuery = true)
    List<SalesDataOutput> findSalesGroupedByMonthAndPlan(LocalDate start, LocalDate end);


    @Query("SELECT new com.application.pennypal.application.dto.output.sale.PaymentStatusDTO(t.status, COUNT(t), SUM(t.amount)) FROM TransactionEntity t WHERE t.transactionDate BETWEEN :start AND :end AND transactionType = 'SUBSCRIPTION' GROUP BY t.status")
    List<PaymentStatusDTO> findPaymentStatusSummary(LocalDate start, LocalDate end);


    @Query("SELECT t FROM TransactionEntity t WHERE t.transactionDate BETWEEN :start AND :end AND t.planId IN :planIds")
    List<TransactionEntity> findByDateRangeAndPlans(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("planIds") List<String> planIds
    );

}
