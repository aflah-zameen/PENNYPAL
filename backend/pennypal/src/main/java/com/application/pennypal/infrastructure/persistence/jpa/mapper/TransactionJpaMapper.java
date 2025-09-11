package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.transaction.entity.Transaction;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CardEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.TransactionEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;

public class TransactionJpaMapper {

    public static Transaction toDomain(TransactionEntity transactionEntity){
        return Transaction.reconstruct(
                transactionEntity.getTransactionId(),
                transactionEntity.getUser().getUserId(),
                transactionEntity.getCategory() == null ? null : transactionEntity.getCategory().getCategoryId(),
                transactionEntity.getPlanId(),
                transactionEntity.getCard() != null ? transactionEntity.getCard().getCardId():null,
                transactionEntity.getAmount(),
                transactionEntity.getTransactionType(),
                transactionEntity.getTitle(),
                transactionEntity.getDescription(),
                transactionEntity.getPaymentMethod(),
                transactionEntity.getTransactionDate(),
                transactionEntity.isFromRecurring(),
                transactionEntity.getRecurringTransactionId(),
                transactionEntity.getTransferToUserId(),
                transactionEntity.getTransferFromUserId(),
                transactionEntity.getReceiverCardId(),
                transactionEntity.getCreatedAt(),
                transactionEntity.getUpdatedAt()
        );
    }
    public static TransactionEntity toEntity(Transaction transaction, UserEntity user, CategoryEntity category, CardEntity card){
        return new TransactionEntity(
                transaction.getTransactionId(),
                user,
                transaction.getTitle(),
                transaction.getAmount(),
                card,
                transaction.getTransactionDate(),
                transaction.getType(),
                transaction.getTransactionStatus(),
                category,
                transaction.getPlanId().orElse(null),
                transaction.getDescription(),
                transaction.getPaymentMethod(),
                transaction.isFromRecurring(),
                transaction.getRecurringTransactionId().orElse(null),
                transaction.getTransferToUserId().orElse(null),
                transaction.getTransferFromUserId().orElse(null),
                transaction.getReceiverCardId().orElse(null)

        );
    }
}
