package com.application.pennypal.domain.transaction.entity;

import com.application.pennypal.domain.valueObject.RecurringLogStatus;
import com.application.pennypal.domain.valueObject.TransactionType;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class RecurringTransactionLog {
    private final String transactionId;
    private final String recurringId;
    private final String userId;
    private final BigDecimal amount;
    private final LocalDate dateFor;
    private final LocalDateTime generatedAt;
    private RecurringLogStatus status;
    private final TransactionType transactionType;
    private final boolean deleted;

    private RecurringTransactionLog(
            String transactionId,
            String recurringId,
            String userId,
            BigDecimal amount,
            LocalDate dateFor,
            LocalDateTime generatedAt,
            RecurringLogStatus recurringLogStatus,
            TransactionType transactionType,
            boolean deleted
    ){
        this.transactionId = transactionId;
        this.recurringId = recurringId;
        this.userId = userId;
        this.amount = amount;
        this.dateFor = dateFor;
        this.generatedAt = generatedAt;
        this.status = recurringLogStatus;
        this.transactionType = transactionType;
        this.deleted = deleted;
    }

    /// Factory method to construct
    public static RecurringTransactionLog create(
            String recurringId,
            String userId,
            BigDecimal amount,
            LocalDate dateFor,
            LocalDateTime generatedAt,
            TransactionType transactionType
    ){

        String transactionId = "REC_LOG_"+UUID.randomUUID();
        RecurringLogStatus logStatus = RecurringLogStatus.PENDING;
        boolean deleted = false;
        return new RecurringTransactionLog(
                transactionId,
                recurringId,
                userId,
                amount,
                dateFor,
                generatedAt,
                logStatus,
                transactionType,
                deleted
        );
    }

    /// Factory method to reconstruct
    public static RecurringTransactionLog reconstruct(
            String transactionId,
            String recurringId,
            String userId,
            BigDecimal amount,
            LocalDate dateFor,
            LocalDateTime generatedAt,
            RecurringLogStatus status,
            TransactionType transactionType,
            boolean deleted
    ){


        return new RecurringTransactionLog(
                transactionId,
                recurringId,
                userId,
                amount,
                dateFor,
                generatedAt,
                status,
                transactionType,
                deleted
        );
    }


    public RecurringTransactionLog updateStatus(RecurringLogStatus status){
        this.status = status;
        return this;
    }

}
