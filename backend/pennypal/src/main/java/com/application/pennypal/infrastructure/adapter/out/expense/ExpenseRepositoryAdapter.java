package com.application.pennypal.infrastructure.adapter.out.expense;

import com.application.pennypal.application.port.out.repository.ExpenseRepositoryPort;
import com.application.pennypal.domain.entity.Expense;
import com.application.pennypal.domain.valueObject.ExpenseDTO;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.category.CategoryRepository;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.ExpenseEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.persistence.jpa.expense.ExpenseRepository;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.CategoryMapper;
import com.application.pennypal.infrastructure.persistence.jpa.user.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

//@Component
//@RequiredArgsConstructor
//public class ExpenseRepositoryAdapter implements ExpenseRepositoryPort {
//    private final ExpenseRepository expenseRepository;
//    private final CategoryMapper categoryMapper;
//    private final CategoryRepository categoryRepository;
//    private final SpringDataUserRepository springDataUserRepository;
//
//    @Override
//    public Expense save(ExpenseDTO expense,Long userId) {
//        UserEntity user = springDataUserRepository.findById(userId)
//                .orElseThrow(()-> new InfrastructureException("User not found","NOT_FOUND"));
//        CategoryEntity category = categoryRepository.findById(expense.category())
//                .orElseThrow(() -> new InfrastructureException("Category not found","NOT_FOUND"));
//        ExpenseEntity entity = new ExpenseEntity(
//                user,expense.name(),category,expense.amount(),expense.type(),expense.startDate(),
//                expense.endDate());
//        entity = expenseRepository.save(entity);
//        return new Expense(entity.getId(),entity.getUser().getId(),entity.getName(),entity.getCategory().getId(),entity.getAmount(),
//                entity.getType(),entity.getStartDate(),entity.getEndDate(),entity.getCreatedAt(),entity.getActive(),entity.isDelete());
//    }
//
//    @Override
//    public Expense save(Expense expense) {
//        UserEntity user = springDataUserRepository.findById(expense.getUserId())
//                .orElseThrow(()-> new UserNotFoundException("User not found"));
//        CategoryEntity category = categoryRepository.findById(expense.getCategoryId())
//                .orElseThrow(() -> new ApplicationException("Category not found","NOT_FOUND"));
//        ExpenseEntity entity = new ExpenseEntity(
//                user,expense.getName(),category,expense.getAmount(),expense.getType()
//                ,expense.getStartDate(),
//                expense.getEndDate());
//        entity.setId(expense.getId());
//        entity.setDelete(expense.isDeleted());
//        entity = expenseRepository.save(entity);
//        return new Expense(entity.getId(),entity.getUser().getId(),entity.getName(),entity.getCategory().getId(),entity.getAmount(),
//                entity.getType(),entity.getStartDate(),entity.getEndDate(),entity.getCreatedAt(),entity.getActive(), entity.isDelete());
//    }
//
//    @Override
//    public List<Expense> fetchAllExpenses(Long userId) {
//        return expenseRepository.findAllByUserIdAndDeleteFalseOrderByCreatedAtDesc(userId).stream()
//                .map(expenseEntity -> new Expense(expenseEntity.getId(),expenseEntity.getUser().getId(),
//                        expenseEntity.getName(),expenseEntity.getCategory().getId(),expenseEntity.getAmount(),
//                        expenseEntity.getType(), expenseEntity.getStartDate(),expenseEntity.getEndDate(),expenseEntity.getCreatedAt(),
//                        expenseEntity.getActive(),expenseEntity.isDelete())).toList();
//    }
//
//    @Override
//    public Optional<Expense> getExpenseById(Long expenseId) {
//        return expenseRepository.findById(expenseId).map(expenseEntity -> new Expense(
//                expenseEntity.getId(),
//                expenseEntity.getUser().getId(),
//                expenseEntity.getName(),
//                expenseEntity.getCategory().getId(),
//                expenseEntity.getAmount(),
//                expenseEntity.getType(),
//                expenseEntity.getStartDate(),
//                expenseEntity.getEndDate(),
//                expenseEntity.getCreatedAt(),
//                expenseEntity.getActive(),
//                expenseEntity.isDelete()
//        ));
//    }
//}
