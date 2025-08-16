package com.application.pennypal.infrastructure.adapter.out.Transaction;

import com.application.pennypal.application.port.out.repository.RecurringTransactionRepositoryPort;
import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import com.application.pennypal.domain.valueObject.TransactionType;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.card.CardRepository;
import com.application.pennypal.infrastructure.persistence.jpa.category.CategoryRepository;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CardEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.RecurringTransactionEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.RecurringTransactionJpaMapper;
import com.application.pennypal.infrastructure.persistence.jpa.transaction.RecurringTransactionRepository;
import com.application.pennypal.infrastructure.persistence.jpa.user.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RecurringTransactionRepositoryAdapter implements RecurringTransactionRepositoryPort {
    private final RecurringTransactionRepository recurringTransactionRepository;
    private final SpringDataUserRepository springDataUserRepository;
    private final CardRepository cardRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Integer countActiveRecurringTransactionByUserId(String userId, TransactionType transactionType) {
        return recurringTransactionRepository.countActiveRecurringTransactionByUserId(userId,transactionType);
    }

    @Override
    public List<RecurringTransaction> findAllActiveRecurringTransactions() {
        return recurringTransactionRepository.findAllByActiveTrueAndDeletedFalse().stream()
                .map(RecurringTransactionJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<RecurringTransaction> getTransactionByIdAndUser(String userId, String recurringId) {
        return recurringTransactionRepository.findByUser_UserIdAndRecurringId(userId,recurringId)
                .map(RecurringTransactionJpaMapper::toDomain);
    }

    @Override
    public RecurringTransaction save(RecurringTransaction transaction) {
        UserEntity user = springDataUserRepository.findByUserId(transaction.getUserId())
                        .orElseThrow(() -> new InfrastructureException("User not found","NOT_FOUND"));
        CardEntity card = null;
        if(transaction.getCardId().isPresent())
            card = cardRepository.findByCardId(transaction.getCardId().get())
                            .orElseThrow(() -> new InfrastructureException("Card not found","NOT_FOUND"));
        CategoryEntity category = categoryRepository.findByCategoryId(transaction.getCategoryId())
                        .orElseThrow(() -> new InfrastructureException("Category not found","NOT_FOUND"));
        RecurringTransactionEntity entity = recurringTransactionRepository.save(RecurringTransactionJpaMapper.toEntity(transaction,user,card,category));
        return RecurringTransactionJpaMapper.toDomain(entity);
    }

    @Override
    public RecurringTransaction update(RecurringTransaction transaction){
        RecurringTransactionEntity oldEntity = recurringTransactionRepository.findByRecurringId(transaction.getRecurringId())
                .orElseThrow(() -> new InfrastructureException("Recurring transaction Jpa Entity not found ","NOT_FOUND"));
        oldEntity.setTitle(transaction.getTitle());
        oldEntity.setAmount(transaction.getAmount());
        oldEntity.setDescription(transaction.getDescription());
        oldEntity.setLastGeneratedDate(transaction.getLastGeneratedDate());
        oldEntity.setEndDate(transaction.getEndDate());
        oldEntity.setActive(transaction.getActive());
        oldEntity.setDeleted(transaction.isDeleted());

        RecurringTransactionEntity newEntity = recurringTransactionRepository.save(oldEntity);
        return RecurringTransactionJpaMapper.toDomain(newEntity);
    }

    @Override
    public List<RecurringTransaction> getAllRecurringTransactions(String userId, TransactionType transactionType) {
        return recurringTransactionRepository.findAllByUser_UserIdAndTransactionTypeAndDeletedFalse(userId,transactionType).stream()
                .map(RecurringTransactionJpaMapper::toDomain)
                .toList();
    }
}
