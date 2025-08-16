package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.transaction.entity.RecurringTransactionLog;
import com.application.pennypal.infrastructure.persistence.jpa.entity.RecurringTransactionLogEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;


public class RecurringLogJpaMapper {
    public static RecurringTransactionLog toDomain (RecurringTransactionLogEntity entity){
        return RecurringTransactionLog.reconstruct(
                entity.getTransactionId(),
                entity.getRecurringId(),
                entity.getUser().getUserId(),
                entity.getAmount(),
                entity.getDateFor(),
                entity.getGeneratedAt(),
                entity.getStatus(),
                entity.getTransactionType(),
                entity.isDeleted()
        );
    }

    public static RecurringTransactionLogEntity toEntity(RecurringTransactionLog transactionLog, UserEntity user){
        return new RecurringTransactionLogEntity(
                transactionLog.getTransactionId(),
                transactionLog.getRecurringId(),
                user,
                transactionLog.getAmount(),
                transactionLog.getDateFor(),
                transactionLog.getGeneratedAt(),
                transactionLog.getStatus(),
                transactionLog.getTransactionType(),
                transactionLog.isDeleted()
        );
    }
}
