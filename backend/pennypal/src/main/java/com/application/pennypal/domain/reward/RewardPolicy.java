package com.application.pennypal.domain.reward;

import com.application.pennypal.domain.shared.exception.NegativeAmountNotAllowedExceptionDomain;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RewardPolicy {
    private final String id;
    private final RewardActionType actionType; // e.g. GOAL_COMPLETION, LOAN_REPAYMENT
    private BigDecimal coinAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;
    private boolean deleted;

    public void updateCoinAmount(BigDecimal newAmount) {
        if (newAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativeAmountNotAllowedExceptionDomain();
        }
        this.coinAmount = newAmount;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate(){
        this.active = true;
    }

    public void markDeleted() {
        this.deleted = true;
    }
}
