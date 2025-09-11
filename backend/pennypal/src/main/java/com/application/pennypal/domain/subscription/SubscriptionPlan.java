package com.application.pennypal.domain.subscription;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriptionPlan {
    private final String planId;
    @Setter
    private String name;
    @Setter
    private String description;
    @Setter
    private BigDecimal amount;
    @Setter
    private int durationDays;
    private List<String> features;
    @Setter
    private boolean deleted;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static SubscriptionPlan create(
            String name,
            String description,
            BigDecimal amount,
            int durationDays,
            List<String> features
    ){
        String planId = "PLAN_"+ UUID.randomUUID();
        return new SubscriptionPlan(
                planId,
                name,
                description,
                amount,
                durationDays,
                features,
                false,
                null,
                null
        );
    }

    public static SubscriptionPlan reconstruct(
            String planId,
            String name,
            String description,
            BigDecimal amount,
            int durationDays,
            List<String> features,
            boolean deleted,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        return new SubscriptionPlan(
                planId,
                name,
                description,
                amount,
                durationDays,
                features,
                deleted,
                createdAt,
                updatedAt
        );
    }
}
