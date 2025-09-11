package com.application.pennypal.infrastructure.persistence.jpa.entity;

import com.application.pennypal.domain.valueObject.PaymentMethod;
import com.application.pennypal.domain.valueObject.TransactionStatus;
import com.application.pennypal.domain.valueObject.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@Getter
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Column(nullable = false,unique = true,updatable = false)
    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private UserEntity user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "card_id",referencedColumnName = "cardId")
    private CardEntity card;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType; //"INCOME","EXPENSE","TRANSFER"


    @Enumerated(EnumType.STRING)
    private TransactionStatus status;


    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "categoryId")
    private CategoryEntity category;

    private String planId;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    // For recurring transactions
    private boolean isFromRecurring;
    private String recurringTransactionId;

//    // For tracking linkage (used for audit or cross-reference)
//    private String referenceId;

    // Transfer specific fields
    private String transferToUserId;
    private String transferFromUserId;
    private String receiverCardId;

    // Audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public TransactionEntity(
            String transactionId,
            UserEntity userEntity,
            String title,
            BigDecimal amount,
            CardEntity card,
            LocalDate transactionDate,
            TransactionType transactionType,
            TransactionStatus transactionStatus,
            CategoryEntity category,
            String planId,
            String description,
            PaymentMethod paymentMethod,
            boolean isFromRecurring,
            String recurringTransactionId,
            String transferToUserId,
            String transferFromUserId,
            String receiverCardId
    ){
        this.transactionId = transactionId;
        this.user = userEntity;
        this.card = card;
        this.title = title;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.status = transactionStatus;
        this.category = category;
        this.planId = planId;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.isFromRecurring = isFromRecurring;
        this.recurringTransactionId  =recurringTransactionId;
        this.transferToUserId = transferToUserId;
        this.transferFromUserId = transferFromUserId;
        this.receiverCardId = receiverCardId;
    }

    public TransactionEntity updateStatus(TransactionStatus status){
        this.status = status;
        return this;
    }
}
