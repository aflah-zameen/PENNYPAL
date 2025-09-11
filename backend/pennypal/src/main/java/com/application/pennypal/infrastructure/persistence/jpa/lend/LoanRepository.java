package com.application.pennypal.infrastructure.persistence.jpa.lend;

import com.application.pennypal.application.dto.output.lend.LoanAdminSummary;
import com.application.pennypal.domain.lend.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<LoanEntity,Long>, JpaSpecificationExecutor<LoanEntity> {
    Optional<LoanEntity> findByLoanId(String loanId);
    @Query("SELECT COALESCE(SUM(l.amount), 0) FROM LoanEntity l WHERE l.lendingRequest.requestedBy = :userId")
    BigDecimal sumTotalAmountLent(@Param("userId") String userId);

    @Query("SELECT COALESCE(SUM(l.amount), 0) FROM LoanEntity l WHERE l.lendingRequest.requestedTo = :userId")
    BigDecimal sumTotalAmountBorrowed(@Param("userId") String userId);

    long countByLendingRequest_RequestedByAndStatusIn(String userId, List<LoanStatus> statuses);

    long countByLendingRequest_RequestedToAndStatusIn(String userId, List<LoanStatus> statuses);

    long countByLendingRequest_RequestedToAndDeadlineBeforeAndStatusIn(String userId, LocalDateTime now, List<LoanStatus> statuses);

    long countByLendingRequest_RequestedByAndDeadlineBeforeAndStatusIn(String userId, LocalDateTime now, List<LoanStatus> active);

    List<LoanEntity> findAllByLendingRequest_RequestedByAndStatusIn(String userId, List<LoanStatus> active);

    List<LoanEntity> findAllByLendingRequest_RequestedToAndStatusIn(String userId, List<LoanStatus> active);

    List<LoanEntity> findAllByOrderByCreatedAtDesc();

    @Query("""
        SELECT new com.application.pennypal.application.dto.output.lend.LoanAdminSummary(
            SUM(l.amount),
            SUM(l.amountPaid),
            COUNT(CASE WHEN l.deadline < CURRENT_TIMESTAMP AND l.status IN ('ACTIVE', 'PARTIAL') THEN 1 END),
            COUNT(CASE WHEN l.status = 'DISPUTED' THEN 1 END)
        )
        FROM LoanEntity l
    """)
    LoanAdminSummary fetchAdminSummary();
}
