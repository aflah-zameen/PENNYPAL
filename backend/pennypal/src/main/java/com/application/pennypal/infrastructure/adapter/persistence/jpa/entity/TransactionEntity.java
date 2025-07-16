package com.application.pennypal.infrastructure.adapter.persistence.jpa.entity;

import com.application.pennypal.domain.valueObject.TransactionStatus;
import com.application.pennypal.domain.valueObject.TransactionOriginType;
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
@Setter
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionOriginType type; //"INCOME","EXPENSE","TRANSFER"

    @Column(nullable = false)
    private Long originId; // Transaction Origin Types ID.

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    private String description;

    private String paymentMethod;

    // For recurring transactions
    private boolean isRecurring;
    private Long recurrenceId;

    // For tracking linkage (used for audit or cross-reference)
    private String referenceId;

    // Transfer specific fields
    @Column(name = "related_user_id")
    private Long relatedUserId;

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

    // Getters and Setters
}
