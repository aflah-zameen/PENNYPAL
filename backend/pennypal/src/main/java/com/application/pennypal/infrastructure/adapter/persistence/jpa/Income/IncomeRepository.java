package com.application.pennypal.infrastructure.adapter.persistence.jpa.Income;

import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<IncomeEntity,Long>, JpaSpecificationExecutor<IncomeEntity> {
    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM IncomeEntity i WHERE i.userId = :userId AND i.incomeDate <= :date")
    BigDecimal getTotalIncomeByUserIdAndDate(Long userId, LocalDate date);
    List<IncomeEntity> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
