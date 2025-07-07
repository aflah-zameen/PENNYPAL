package com.application.pennypal.infrastructure.adapter.expense;

import com.application.pennypal.application.port.ExpenseRepositoryPort;
import com.application.pennypal.domain.user.entity.Expense;
import com.application.pennypal.domain.user.valueObject.ExpenseDTO;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.category.CategoryRepository;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.ExpenseEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.expense.ExpenseRepository;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper.CategoryMapper;
import com.application.pennypal.shared.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExpenseRepositoryAdapter implements ExpenseRepositoryPort {
    private final ExpenseRepository expenseRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public Expense save(ExpenseDTO expense,Long userId) {
        CategoryEntity category = categoryRepository.findById(expense.category())
                .orElseThrow(() -> new ApplicationException("Category not found","NOT_FOUND"));
        ExpenseEntity entity = new ExpenseEntity(
                userId,expense.name(),category,expense.amount(),expense.type(),expense.startDate(),
                expense.endDate());
        entity = expenseRepository.save(entity);
        return new Expense(entity.getId(),entity.getName(),categoryMapper.toDomain(entity.getCategory()),entity.getAmount(),
                entity.getType(),entity.getStartDate(),entity.getEndDate(),entity.getCreatedAt(),entity.getActive());
    }

    @Override
    public List<Expense> fectchAllExpenses(Long userId) {
        return expenseRepository.findAllByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(expenseEntity -> new Expense(expenseEntity.getId(),expenseEntity.getName(),
                        categoryMapper.toDomain(expenseEntity.getCategory()),expenseEntity.getAmount(),expenseEntity.getType(),
                        expenseEntity.getStartDate(),expenseEntity.getEndDate(),expenseEntity.getCreatedAt(),
                        expenseEntity.getActive())).toList();
    }
}
