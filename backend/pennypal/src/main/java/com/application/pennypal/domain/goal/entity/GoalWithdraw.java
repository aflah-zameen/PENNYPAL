package com.application.pennypal.domain.goal.entity;

import com.application.pennypal.domain.valueObject.GoalWithdrawRequestStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class GoalWithdraw {
    private final String id;
    private final String email;
    private final String goalId;
    private final BigDecimal amount;
    private GoalWithdrawRequestStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private GoalWithdraw(
            String id,
            String email,
            String goalId,
            BigDecimal amount,
            GoalWithdrawRequestStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        this.id = id;
        this.email = email;
        this.goalId = goalId;
        this.amount = amount;
        this.status = status;
        this.createdAt =createdAt;
        this.updatedAt = updatedAt;
    }

    /// static factory method
    public static GoalWithdraw create(
            String goalId,
            String email,
            BigDecimal amount,
            GoalWithdrawRequestStatus status
    ){
        String id = "WITH_"+ UUID.randomUUID();
        return new GoalWithdraw(
                id,
                email,
                goalId,
                amount,
                status,
                null,
                null
        );
    }

    /// static factory method
    public static GoalWithdraw reconstruct(
            String id,
            String email,
            String goalId,
            BigDecimal amount,
            GoalWithdrawRequestStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        return new GoalWithdraw(
                id,
                email,
                goalId,
                amount,
                status,
                createdAt,
                updatedAt
        );
    }

    /// Business methods

    public GoalWithdraw updateStatus(GoalWithdrawRequestStatus status){
        this.status = status;
        return this;
    }
}
