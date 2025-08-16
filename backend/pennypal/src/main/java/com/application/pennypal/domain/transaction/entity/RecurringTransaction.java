package com.application.pennypal.domain.transaction.entity;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;
import com.application.pennypal.domain.valueObject.RecurrenceFrequency;
import com.application.pennypal.domain.valueObject.TransactionType;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Getter
public class RecurringTransaction {
    private final String recurringId;
    private final String userId;
    private String cardId;
    private String categoryId;
    private final TransactionType transactionType;
    private String title;
    private String description;
    private BigDecimal amount;
    private final RecurrenceFrequency frequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate lastGeneratedDate;
    private Boolean active ;
    private boolean deleted = false;

    /// Auditing fields
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private RecurringTransaction(
            String recurringId,
            String userId,
            String cardId,
            String categoryId,
            TransactionType transactionType,
            String title,
            String description,
            BigDecimal amount,
            RecurrenceFrequency frequency,
            LocalDate startDate,
            LocalDate endDate,
            LocalDate lastGeneratedDate,
            boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt

    ){
        this.recurringId = recurringId;
        this.userId=userId;
        this.cardId = cardId;
        this.categoryId = categoryId;
        this.transactionType=transactionType;
        this.title  = title;
        this.description = description;
        this.amount = amount;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastGeneratedDate = lastGeneratedDate;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /// Factory method to create
    public static RecurringTransaction create(
            String userId,
            String cardId,
            String categoryId,
            TransactionType type,
            String title,
            String description,
            BigDecimal amount,
            RecurrenceFrequency frequency,
            LocalDate startDate,
            LocalDate endStart
    ){
        /// Validation

        String recurringId = "REC_"+ UUID.randomUUID();

        return new RecurringTransaction(
                recurringId,
                userId,
                cardId,
                categoryId,
                type,
                title,
                description,
                amount,
                frequency,
                startDate,
                endStart,
                null,
                true,
                null,
                null
        );
    }

    /// Factory method to reconstruct
    public static  RecurringTransaction reconstruct(
            String recurringId,
            String userId,
            String cardId,
            String categoryId,
            TransactionType type,
            String title,
            String description,
            BigDecimal amount,
            RecurrenceFrequency frequency,
            LocalDate startDate,
            LocalDate endStart,
            LocalDate lastGeneratedDate,
            boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        return new RecurringTransaction(
                recurringId,
                userId,
                cardId,
                categoryId,
                type,
                title,
                description,
                amount,
                frequency,
                startDate,
                endStart,
                lastGeneratedDate,
                active,
                createdAt,
                updatedAt
        );
    }

    /// Business methods

    public RecurringTransaction updateTitle(String title){
        this.title = title;
        return this;
    }

    public RecurringTransaction updateCardId(String cardId){
        this.cardId = cardId;
        return this;
    }

    public RecurringTransaction updateCategoryId(String categoryId){
        this.categoryId =categoryId;
        return this;
    }

    public RecurringTransaction updateDescription(String description){
        this.description = description;
        return this;
    }

    public RecurringTransaction updateAmount(BigDecimal amount){
        this.amount = amount;
        return this;
    }



//    public RecurringTransaction updateInterval(LocalDate startDate,LocalDate endStart){
//        this.startDate = startDate;
//        this.endStart = endStart;
//        return this ;
//    }
    public RecurringTransaction extendEndDate(LocalDate endDate){
        if(this.endDate.isAfter(endDate))
            throw new DomainBusinessException("Extend request end date is before the last date", DomainErrorCode.INVALID_DATE);
        this.endDate = endDate;
        return this;
    }

    public RecurringTransaction updateLastGeneratedDate(LocalDate generatedDate){
        this.lastGeneratedDate = generatedDate;
        return this;
    }

    public RecurringTransaction toggleStatus(){
        this.active = !this.active;
        return this;
    }

    public RecurringTransaction delete(){
        this.deleted = true;
        return this;
    }

    /// Getters

    public Optional<String> getCardId(){
        return Optional.ofNullable(cardId);
    }

}
