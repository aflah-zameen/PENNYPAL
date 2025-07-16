package com.application.pennypal.infrastructure.adapter.Transaction;

import com.application.pennypal.application.port.TransactionRepositoryPort;
import com.application.pennypal.domain.entity.Transactions;
import com.application.pennypal.domain.valueObject.TransactionOriginType;
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
    public Transactions save(Transactions transactions) {
        UserEntity user = springDataUserRepository.findById(transactions.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        CategoryEntity category = categoryRepository.findById(transactions.getCategoryId())
                .orElseThrow(() -> new ApplicationException("Category not found","NOT_FOUND"));
        TransactionEntity transactionEntity = transactionRepository.save(transactionMapper.toEntity(transactions,user,category));
        return transactionMapper.toDomain(transactionEntity);
    }

    @Override
    public boolean exitsDuplicateTransaction(Long userId, BigDecimal amount, LocalDate transactionDate, TransactionOriginType type, Long originId, Long categoryId) {
        return transactionRepository.existsByUserIdAndAmountAndTransactionDateAndTypeAndOriginIdAndCategoryId(userId,amount,transactionDate,type,originId,categoryId);
    }

    @Override
    public List<Transactions> findRecentIncomeTransaction(Long userId, int size) {
        Pageable pageable = PageRequest.of(0,size);
        return transactionRepository.findRecentIncomeTransactions(userId,TransactionOriginType.INCOME,pageable).stream()
                .map(transactionMapper::toDomain).toList();
    }

    @Override
    public List<Transactions> findAllByUserIdAndOriginIdAndType(Long userId, Long originId,TransactionOriginType type) {
        return transactionRepository.findAllByUserIdAndOriginIdAndTypeOrderByCreatedAtDesc(userId,originId,type).stream()
                .map(transactionMapper::toDomain)
                .toList();
    }
}
