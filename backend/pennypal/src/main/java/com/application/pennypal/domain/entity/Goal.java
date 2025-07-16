package com.application.pennypal.domain.entity;

import com.application.pennypal.domain.valueObject.GoalStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Goal {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String description;
    private final BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private GoalStatus status;
    private final Long categoryId;
    private final int priorityLevel;
    private boolean deleted;

    /// Auditing properties
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Goal(Long id, Long userId, String title, BigDecimal targetAmount, LocalDate startDate, LocalDate endDate, Long categoryId, int priorityLevel, String description) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.targetAmount = targetAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.categoryId = categoryId;
        this.priorityLevel = priorityLevel;
        this.description = description;
        this.currentAmount = BigDecimal.ZERO;

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
}
