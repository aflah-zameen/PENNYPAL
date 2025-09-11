package com.application.pennypal.infrastructure.persistence.jpa.coin.entity;
import com.application.pennypal.domain.coin.RedemptionRequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "redemption_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RedemptionRequestEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal coinsRedeemed;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal realMoney;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RedemptionRequestStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime approvedAt;

    private String approvedBy;
}

