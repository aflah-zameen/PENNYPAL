package com.application.pennypal.domain.subscription;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriptionPlan {
    private final String planId;
    private String name;
    private BigDecimal amount;
    private int durationDays;
    private List<String> features;
    private final LocalTime createdAt;
    private final LocalDateTime updatedAt;

    public SubscriptionPlan
}
