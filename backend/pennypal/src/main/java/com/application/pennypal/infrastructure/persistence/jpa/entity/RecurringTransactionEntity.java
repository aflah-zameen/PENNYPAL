package com.application.pennypal.infrastructure.persistence.jpa.entity;

import com.application.pennypal.domain.valueObject.RecurrenceFrequency;
import com.application.pennypal.domain.valueObject.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "recurring_transactions")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class RecurringTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,updatable = false,unique = true)
    private String recurringId;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "card_id",referencedColumnName = "cardId")
    private CardEntity card;

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "categoryId")
    private CategoryEntity category;

    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    @Setter
    private String title;

    @Setter
    private String description;

    @Setter
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private RecurrenceFrequency frequency;

    @Column(nullable = false)
    private LocalDate startDate;

    @Setter
    @Column(nullable = false)
    private LocalDate endDate;

    @Setter
    private LocalDate lastGeneratedDate;

    @Setter
    @Column(nullable = false)
    private Boolean active;

    @Setter
    private boolean deleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    /// Factory method to create
    public static  RecurringTransactionEntity create(
            String recurringId,
            UserEntity user,
            CardEntity card,
            CategoryEntity category,
            TransactionType transactionType,
            String title,
            String description,
            BigDecimal amount,
            RecurrenceFrequency frequency,
            LocalDate startDate,
            LocalDate endDate,
            LocalDate lastGeneratedDate,
            boolean active,
            boolean deleted
    ){
        return new RecurringTransactionEntity(
                null,
                recurringId,
                user,
                card,
                category,
                transactionType,
                title,
                description,
                amount,
                frequency,
                startDate,
                endDate,
                lastGeneratedDate,
                active,
                deleted,
                null,
                null
        );
    }



}
