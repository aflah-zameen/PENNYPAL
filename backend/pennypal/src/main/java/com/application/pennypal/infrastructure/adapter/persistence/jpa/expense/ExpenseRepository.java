package com.application.pennypal.infrastructure.adapter.persistence.jpa.expense;

import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity,Long> {
    List<ExpenseEntity> findAllByUserIdAndDeleteFalseOrderByCreatedAtDesc(Long userId);
}
