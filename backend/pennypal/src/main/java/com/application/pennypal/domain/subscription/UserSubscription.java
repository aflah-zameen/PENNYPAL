package com.application.pennypal.domain.subscription;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class UserSubscription {
    private final String subscriptionId;
    private final String userId;
    private final String planId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private boolean isActive;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static UserSubscription create(
            String userId,
            String planId,
            LocalDate startDate,
            LocalDate endDate
    ){
        String subscriptionId = "SUBS_"+ UUID.randomUUID();
        return new UserSubscription(
                subscriptionId,
                userId,
                planId,
                startDate,
                endDate,
                true,
                null,
                null
        );
    }

    public static UserSubscription reconstruct(
            String subscriptionId,
            String userId,
            String planId,
            LocalDate startDate,
            LocalDate endDate,
            boolean isActive,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        return new UserSubscription(
                subscriptionId,
                userId,
                planId,
                startDate,
                endDate,
                isActive,
                createdAt,
                updatedAt
        );
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(endDate);
    }

    public void deactivate() {
        this.isActive = false;
    }

}
