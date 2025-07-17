package com.application.pennypal.domain.entity;
import com.application.pennypal.domain.exception.category.CategoryRequiredExceptionDomain;
import com.application.pennypal.domain.exception.shared.MissingAmountExceptionDomain;
import com.application.pennypal.domain.exception.shared.MissingCreatedAtExceptionDomain;
import com.application.pennypal.domain.exception.shared.NegativeAmountNotAllowedExceptionDomain;
import com.application.pennypal.domain.exception.transaction.*;
import com.application.pennypal.domain.exception.user.MissingUserIdExceptionDomain;
import com.application.pennypal.domain.valueObject.PaymentMethod;
import com.application.pennypal.domain.valueObject.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class Transaction {

    private final String transactionId;
    private final Long userId;
    private final Long categoryId;
    private final Long cardId;
    private final BigDecimal amount;
    private final TransactionType type;
    private final String title;
    private final String description;
    private final PaymentMethod paymentMethod;
    private final LocalDate transactionDate;
    private final boolean isFromRecurring;
    private final Long recurringTransactionId;
    private final Long transferToUserId;
    private final Long transferFromUserId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    // Constructor is private to enforce invariants via static factory method
    private Transaction(
            String transactionId,
            Long userId,
            Long categoryId,
            Long cardId,
            BigDecimal amount,
            TransactionType type,
            String title,
            String description,
            PaymentMethod paymentMethod,
            LocalDate transactionDate,
            boolean isFromRecurring,
            Long recurringTransactionId,
            Long transferToUserId,
            Long transferFromUserId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        validate(transactionId, userId, amount, type, transactionDate, createdAt,isFromRecurring,recurringTransactionId,categoryId);

        this.transactionId = transactionId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.cardId = cardId;
        this.amount = amount;
        this.type = type;
        this.title = title;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.transactionDate = transactionDate;
        this.isFromRecurring = isFromRecurring;
        this.recurringTransactionId = recurringTransactionId;
        this.transferToUserId = transferToUserId;
        this.transferFromUserId = transferFromUserId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Public static factory method (recommended in DDD for complex entities)
    public static Transaction create(
            String transactionId,
            Long userId,
            Long categoryId,
            Long cardId,
            BigDecimal amount,
            TransactionType type,
            String title,
            String description,
            PaymentMethod paymentMethod,
            LocalDate transactionDate,
            boolean isFromRecurring,
            Long recurringTransactionId,
            Long transferToUserId,
            Long transferFromUserId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        return new Transaction(
                transactionId, userId, categoryId, cardId, amount, type,
                title, description, paymentMethod, transactionDate,
                isFromRecurring, recurringTransactionId,
                transferToUserId, transferFromUserId,
                createdAt, updatedAt
        );
    }

    // Domain validation logic
    private void validate(String transactionId, Long userId, BigDecimal amount,
                          TransactionType type, LocalDate transactionDate, LocalDateTime createdAt,
                          Boolean isFromRecurring,Long recurringTransactionId,
                          Long categoryId) {

        if(transactionId == null)
            throw new MissingTransactionIdExceptionDomain();
        if(userId==null)
            throw new MissingUserIdExceptionDomain();
        if(amount==null)
            throw new MissingAmountExceptionDomain();
        if(type == null)
            throw new MissingTransactionTypeExceptionDomain();
        if(transactionDate==null)
            throw new MissingTransactionDateExceptionDomain();
        if(createdAt == null)
            throw new MissingCreatedAtExceptionDomain();

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeAmountNotAllowedExceptionDomain();
        }

        if (type == TransactionType.TRANSFER) {
            if (transferToUserId == null || transferFromUserId == null) {
                throw new TransferUsersRequiredExceptionDomain();
            }
        }

        if(type == TransactionType.INCOME || type == TransactionType.EXPENSE){
            if(categoryId == null){
                throw new CategoryRequiredExceptionDomain();
            }
        }
        if(isFromRecurring != null && recurringTransactionId == null){
            throw  new MissingRecurringReferenceExceptionDomain();
        }
    }

    // Business methods
    public boolean isTransfer() {
        return type == TransactionType.TRANSFER;
    }

    public boolean isIncome() {
        return type == TransactionType.INCOME;
    }

    public boolean isExpense() {
        return type == TransactionType.EXPENSE;
    }

    public boolean isFromRecurringTransaction() {
        return isFromRecurring && recurringTransactionId != null;
    }

    public boolean isSelfTransfer() {
        return (transferToUserId != null &&
                transferFromUserId != null) &&
                transferToUserId.equals(transferFromUserId);
    }

    // Optional Getters for nullable fields
    public Optional<Long> getRecurringTransactionId() {
        return Optional.ofNullable(recurringTransactionId);
    }

    public Optional<Long> getTransferToUserId() {
        return Optional.ofNullable(transferToUserId);
    }

    public Optional<Long> getTransferFromUserId() {
        return Optional.ofNullable(transferFromUserId);
    }

    public Optional<Long> getCardId() {
        return Optional.ofNullable(cardId);
    }

    public Optional<Long> getCategoryId() {
        return Optional.ofNullable(categoryId);
    }

    // Standard getters
    public String getTransactionId() { return transactionId; }
    public Long getUserId() { return userId; }
    public BigDecimal getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public LocalDate getTransactionDate() { return transactionDate; }
    public boolean isFromRecurring() { return isFromRecurring; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
