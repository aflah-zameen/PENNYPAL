package com.application.pennypal.domain.entity;

import com.application.pennypal.domain.valueObject.TransactionStatus;
import com.application.pennypal.domain.valueObject.TransactionOriginType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class Transactions {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private TransactionOriginType type;
    private Long originId;
    private TransactionStatus status;
    private Long categoryId;
    private String description;
    private String paymentMethod;

    // For tracking linkage (used for audit or cross-reference)
    private boolean isRecurring;
    private Long recurrenceId;

    // For tracking linkage (used for audit or cross-reference)
    private String referenceId;

    private Long relatedUserId;

    // Audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public Transactions(Long userId,BigDecimal amount,LocalDate transactionDate,TransactionOriginType type,Long originId,
                        TransactionStatus status,Long categoryId,String description,String paymentMethod, boolean isRecurring,
                        Long recurrenceId,String referenceId,Long relatedUserId){
        this.userId = userId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.type = type;
        this.originId = originId;
        this.status = status;
        this.categoryId = categoryId;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.isRecurring = isRecurring;
        this.recurrenceId = recurrenceId;
        this.referenceId = referenceId;
        this.relatedUserId = relatedUserId;
    }
}
