package com.application.pennypal.domain.coin;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class RedemptionRequest {
    private final String id;
    private final String userId;
    private final BigDecimal coinsRedeemed;
    private final BigDecimal realMoney;
    private RedemptionRequestStatus status;
    private  final LocalDateTime createdAt;
    private LocalDateTime approvedAt;
    private String approvedBy;

    public void approve(String adminId, LocalDateTime approvedAt) {
        if (this.status != RedemptionRequestStatus.PENDING) {
            throw new IllegalStateException("Redemption request is not pending");
        }
        this.status = RedemptionRequestStatus.APPROVED;
        this.approvedBy = adminId;
        this.approvedAt = approvedAt;
    }

    public void reject(String adminId, LocalDateTime approvedAt) {
        if (this.status != RedemptionRequestStatus.PENDING) {
            throw new IllegalStateException("Redemption request is not pending");
        }
        this.status = RedemptionRequestStatus.REJECTED;
        this.approvedBy = adminId;
        this.approvedAt = approvedAt;
    }
}
