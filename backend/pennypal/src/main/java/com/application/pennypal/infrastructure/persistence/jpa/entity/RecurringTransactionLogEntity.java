package com.application.pennypal.infrastructure.persistence.jpa.entity;

import com.application.pennypal.domain.valueObject.RecurringLogStatus;
import com.application.pennypal.domain.valueObject.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "recurring_logs")
public class RecurringTransactionLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Column(nullable = false,unique = true,updatable = false)
    private String transactionId;

    @Column(nullable = false)
    private String recurringId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private UserEntity user;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate dateFor;

    @Column(nullable = false)
    private LocalDateTime generatedAt;

    @Enumerated(EnumType.STRING)
    private RecurringLogStatus status;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private boolean deleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public RecurringTransactionLogEntity(String transactionId,String recurringId,UserEntity user, BigDecimal amount,
                                    LocalDate dateFor,LocalDateTime generatedAt, RecurringLogStatus status, TransactionType transactionType,
                                    boolean deleted){
        this.transactionId = transactionId;
        this.recurringId = recurringId;
        this.user = user;
        this.amount = amount;
        this.dateFor =dateFor;
        this.generatedAt = generatedAt;
        this.status = status;
        this.transactionType = transactionType;
        this.deleted  =deleted;
    }

    protected  RecurringTransactionLogEntity(){
    }


    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected  void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }



}
