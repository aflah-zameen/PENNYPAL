package com.application.pennypal.domain.transaction.entity;
import com.application.pennypal.domain.exception.category.CategoryRequiredExceptionDomain;
import com.application.pennypal.domain.shared.exception.MissingAmountExceptionDomain;
import com.application.pennypal.domain.shared.exception.MissingCreatedAtExceptionDomain;
import com.application.pennypal.domain.shared.exception.NegativeAmountNotAllowedExceptionDomain;
import com.application.pennypal.domain.exception.transaction.*;
import com.application.pennypal.domain.user.exception.validation.MissingUserIdDomainException;
import com.application.pennypal.domain.valueObject.PaymentMethod;
import com.application.pennypal.domain.valueObject.TransactionStatus;
import com.application.pennypal.domain.valueObject.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class Transaction {

    private final String transactionId ;
    private final String userId;
    private final String categoryId;
    private final String planId;
    private final String cardId;
    private final BigDecimal amount;
    private final TransactionType type;
    private final String title;
    private final String description;
    private final PaymentMethod paymentMethod;
    private final LocalDate transactionDate;
    private final boolean isFromRecurring;
    private final String recurringTransactionId;
    private final String transferToUserId;
    private final String transferFromUserId;
    private final String receiverCardId;
    private final TransactionStatus transactionStatus;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    // Constructor is private to enforce invariants via static factory method
    private Transaction(
            String transactionId,
            String userId,
            String categoryId,
            String planId,
            String cardId,
            BigDecimal amount,
            TransactionType type,
            String title,
            String description,
            PaymentMethod paymentMethod,
            LocalDate transactionDate,
            boolean isFromRecurring,
            String recurringTransactionId,
            String transferToUserId,
            String transferFromUserId,
            String receiverCardId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        validate(transactionId,userId, amount,paymentMethod,cardId, type, transactionDate,isFromRecurring,recurringTransactionId,transferToUserId,transferFromUserId,receiverCardId,categoryId);

        this.transactionId = transactionId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.planId = planId;
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
        this.receiverCardId = receiverCardId;
        this.transactionStatus  = TransactionStatus.PENDING;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Public static factory method
    public static Transaction create(
            String userId,
            String categoryId,
            String cardId,
            String planId,
            BigDecimal amount,
            TransactionType type,
            String title,
            String description,
            PaymentMethod paymentMethod,
            LocalDate transactionDate,
            boolean isFromRecurring,
            String recurringTransactionId,
            String transferToUserId,
            String transferFromUserId,
            String receiverCardId
    ) {
        String transactionId = "TRX_"+ UUID.randomUUID();
        return new Transaction(
                transactionId,
                userId, categoryId,planId, cardId, amount, type,
                title, description, paymentMethod, transactionDate,
                isFromRecurring, recurringTransactionId,
                transferToUserId, transferFromUserId,receiverCardId,
                null, null
        );
    }

    public static Transaction reconstruct(
            String transactionId,
            String userId,
            String categoryId,
            String planId,
            String cardId,
            BigDecimal amount,
            TransactionType type,
            String title,
            String description,
            PaymentMethod paymentMethod,
            LocalDate transactionDate,
            boolean isFromRecurring,
            String recurringTransactionId,
            String transferToUserId,
            String transferFromUserId,
            String receiverCardId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){

        return new Transaction(
                transactionId,
                userId, categoryId,planId, cardId, amount, type,
                title, description, paymentMethod, transactionDate,
                isFromRecurring, recurringTransactionId,
                transferToUserId, transferFromUserId,receiverCardId,
                createdAt, updatedAt
        );
    }

    // Domain validation logic
    private void validate(String transactionId, String userId, BigDecimal amount,
                          PaymentMethod paymentMethod,String cardId,
                          TransactionType type, LocalDate transactionDate,
                          Boolean isFromRecurring,String recurringTransactionId,
                          String transferToUserId,
                          String transferFromUserId,
                          String receiverCardId,
                          String categoryId) {

        if(transactionId == null)
            throw new MissingTransactionIdExceptionDomain();
        if(userId==null)
            throw new MissingUserIdDomainException();
        if(amount==null)
            throw new MissingAmountExceptionDomain();
        if(type == null)
            throw new MissingTransactionTypeExceptionDomain();
        if(transactionDate==null)
            throw new MissingTransactionDateExceptionDomain();

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeAmountNotAllowedExceptionDomain();
        }

        if (type == TransactionType.TRANSFER) {
            if (transferToUserId == null || transferFromUserId == null || receiverCardId == null) {
                throw new TransferUsersRequiredExceptionDomain();
            }
        }

        if(type == TransactionType.INCOME || type == TransactionType.EXPENSE){
            if(categoryId == null){
                throw new CategoryRequiredExceptionDomain();
            }
        }
        if(isFromRecurring && recurringTransactionId     == null){
            throw  new MissingRecurringReferenceExceptionDomain();
        }

        if(paymentMethod == null){
            throw new MissingPaymentMethodExceptionDomain();
        }


//        if(cardId != null && paymentMethod != PaymentMethod.CARD){
//            throw new UnsupportedPaymentCombinationException();
//        }
//
//        if(cardId == null && paymentMethod != PaymentMethod.WALLET){
//            throw new UnsupportedPaymentCombinationException();
//        }

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
    public Optional<String> getRecurringTransactionId() {
        return Optional.ofNullable(recurringTransactionId);
    }

    public Optional<String> getTransferToUserId() {
        return Optional.ofNullable(transferToUserId);
    }

    public Optional<String> getTransferFromUserId() {
        return Optional.ofNullable(transferFromUserId);
    }

    public Optional<String> getCardId() {
        return Optional.ofNullable(cardId);
    }

    public Optional<String> getPlanId() {
        return Optional.ofNullable(planId);
    }


    public Optional<String> getReceiverCardId(){
        return Optional.ofNullable(receiverCardId);
    }


    // Standard getters
    public Optional<String> getCategoryId() {return Optional.ofNullable(categoryId);}

    public String getTransactionId() { return transactionId; }
    public TransactionStatus getTransactionStatus(){return transactionStatus;}
    public String getUserId() { return userId; }
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
