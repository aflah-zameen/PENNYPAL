package com.application.pennypal.domain.goal.entity;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class GoalContribution {
    private final String contributionId;
    private final String userId;
    private final String goalId;
    private final String cardId;
    private final String transactionId;
    private final BigDecimal amount;
    private final LocalDateTime date;
    private final String notes;

    private GoalContribution(String contributionId,
                             String userId,
                             String goalId,
                             String cardId,
                             String transactionId,
                             BigDecimal amount,
                             LocalDateTime date,
                             String notes){
        this.contributionId = contributionId;
        this.userId = userId;
        this.goalId = goalId;
        this.cardId = cardId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.date = date;
        this.notes = notes;
    }


    /// Static factory methods
    public static GoalContribution create(String userId,
                                          String goalId,
                                          String cardId,
                                          String transactionId,
                                          BigDecimal amount,
                                          String notes
                                          ){
        String contributionId = "CONT_"+ UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();
        return new GoalContribution(
                contributionId,
                userId,
                goalId,
                cardId,
                transactionId,
                amount,
                date,
                notes
        );
    }

    public static GoalContribution reconstruct(
                                               String contributionId,
                                               String userId,
                                               String goalId,
                                               String cardId,
                                               String transactionId,
                                               BigDecimal amount,
                                               LocalDateTime date,
                                               String notes){
        return new GoalContribution(
                contributionId,
                userId,
                goalId,
                cardId,
                transactionId,
                amount,
                date,
                notes
        );
    }

}
