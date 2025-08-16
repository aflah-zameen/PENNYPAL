package com.application.pennypal.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "GoalContributionEntity")
@Getter
public class GoalContributionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,updatable = false,unique = true)
    private String contributionId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String goalId;

    private String cardId;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime date;

    private String notes;

    public GoalContributionEntity(String contributionId,String userId, String goalId, String transactionId, BigDecimal amount,LocalDateTime date){
        this.contributionId = contributionId;
        this.userId = userId;
        this.goalId = goalId;
        this.transactionId = transactionId;
        this.amount=amount;
        this.date = date;
    }

    GoalContributionEntity(){}


}
