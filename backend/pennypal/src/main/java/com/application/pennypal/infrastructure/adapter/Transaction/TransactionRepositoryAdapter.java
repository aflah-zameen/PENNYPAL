package com.application.pennypal.infrastructure.adapter.Transaction;

import com.application.pennypal.application.port.TransactionRepositoryPort;
import com.application.pennypal.domain.entity.Transaction;
import com.application.pennypal.domain.valueObject.TransactionType;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.category.CategoryRepository;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.TransactionEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper.TransactionMapper;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.transaction.TransactionRepository;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.user.SpringDataUserRepository;
import com.application.pennypal.shared.exception.ApplicationException;
import com.application.pennypal.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Component
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {
    private final TransactionRepository transactionRepository;
    private final SpringDataUserRepository springDataUserRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionMapper transactionMapper;
    @Override
    public BigDecimal getTotalAmountForMonth(LocalDate start, LocalDate end) {
        return transactionRepository.getTotalAmountForMonth(start,end);
    }

    @Override
    public Transaction save(Transaction transaction) {
        UserEntity user = springDataUserRepository.findById(transaction.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        CategoryEntity category = categoryRepository.findById(transaction.getCategoryId())
                .orElseThrow(() -> new ApplicationException("Category not found","NOT_FOUND"));
        TransactionEntity transactionEntity = transactionRepository.save(transactionMapper.toEntity(transaction,user,category));
        return transactionMapper.toDomain(transactionEntity);
    }

    @Override
    public boolean exitsDuplicateTransaction(Long userId, BigDecimal amount, LocalDate transactionDate, TransactionType type, Long originId, Long categoryId) {
        return transactionRepository.existsByUserIdAndAmountAndTransactionDateAndTypeAndOriginIdAndCategoryId(userId,amount,transactionDate,type,originId,categoryId);
    }

    @Override
    public List<Transaction> findRecentIncomeTransaction(Long userId, int size) {
        Pageable pageable = PageRequest.of(0,size);
        return transactionRepository.findRecentIncomeTransactions(userId, TransactionType.INCOME,pageable).stream()
                .map(transactionMapper::toDomain).toList();
    }

    @Override
    public List<Transaction> findAllByUserIdAndOriginIdAndType(Long userId, Long originId, TransactionType type) {
        return transactionRepository.findAllByUserIdAndOriginIdAndTypeOrderByCreatedAtDesc(userId,originId,type).stream()
                .map(transactionMapper::toDomain)
                .toList();
    }
}
