package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CardEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.RecurringTransactionEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;

public class RecurringTransactionJpaMapper {
    public static RecurringTransaction toDomain(RecurringTransactionEntity entity){
        return RecurringTransaction.reconstruct(
                entity.getRecurringId(),
                entity.getUser().getUserId(),
                entity.getCard() != null ? entity.getCard().getCardId():null,
                entity.getCategory().getCategoryId(),
                entity.getTransactionType(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getAmount(),
                entity.getFrequency(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getLastGeneratedDate(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static RecurringTransactionEntity toEntity(RecurringTransaction transaction, UserEntity user, CardEntity card, CategoryEntity category){
        return RecurringTransactionEntity.create(
                transaction.getRecurringId(),
                user,
                card,
                category,
                transaction.getTransactionType(),
                transaction.getTitle(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getFrequency(),
                transaction.getStartDate(),
                transaction.getEndDate(),
                transaction.getLastGeneratedDate(),
                transaction.getActive(),
                transaction.isDeleted()
                );
    }
}
