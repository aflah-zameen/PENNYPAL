package com.application.pennypal.domain.goal.entity;

import com.application.pennypal.domain.valueObject.GoalStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Goal {
    private final String goalId;
    private final String userId;
    private String title;
    private String description;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private GoalStatus status;
    private String categoryId;
    private int priorityLevel;
    private boolean deleted;

    /// Auditing properties
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Goal(String goalId, String userId, String title, BigDecimal targetAmount,BigDecimal currentAmount, LocalDate startDate, LocalDate endDate, String categoryId, int priorityLevel, String description,GoalStatus status,boolean deleted,LocalDateTime createdAt,LocalDateTime updatedAt) {
        this.goalId = goalId;
        this.userId = userId;
        this.title = title;
        this.targetAmount = targetAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.categoryId = categoryId;
        this.priorityLevel = priorityLevel;
        this.description = description;
        this.currentAmount = currentAmount;
        this.status = GoalStatus.COMPLETED;
        this.deleted = deleted;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Goal create( String userId, String title, BigDecimal targetAmount, LocalDate startDate, LocalDate endDate, String categoryId, int priorityLevel, String description

    ){
        String goalId ="GOA_"+ UUID.randomUUID();
        return new Goal(
                goalId,
                userId,
                title,
                targetAmount,
                BigDecimal.ZERO,
                startDate,
                endDate,
                categoryId,
                priorityLevel,
                description,
                GoalStatus.COMPLETED,
                false,
                null,
                null
        );
    }

    public static Goal reconstruct(String goalId, String userId, String title, BigDecimal targetAmount, BigDecimal currentAmount, LocalDate startDate, LocalDate endDate, String categoryId, int priorityLevel, String description,GoalStatus status,boolean deleted,LocalDateTime createdAt,LocalDateTime updatedAt){
        return new Goal(
                goalId,
                userId,
                title,
                targetAmount,
                currentAmount,
                startDate,
                endDate,
                categoryId,
                priorityLevel,
                description,
                status,
                deleted,
                createdAt,
                updatedAt
        );
    }

    public void contribute(BigDecimal amount) {
        if (status != GoalStatus.ACTIVE) {
            throw new IllegalStateException("Cannot contribute to a goal that is not active.");
        }

        this.currentAmount = this.currentAmount.add(amount);
        if (this.currentAmount.compareTo(targetAmount) >= 0) {
            this.status = GoalStatus.COMPLETED;
        }
    }

    public boolean isCompleted() {
        return this.status == GoalStatus.COMPLETED;
    }

    public Goal delete(){
        this.deleted = true;
        return this;
    }
}
