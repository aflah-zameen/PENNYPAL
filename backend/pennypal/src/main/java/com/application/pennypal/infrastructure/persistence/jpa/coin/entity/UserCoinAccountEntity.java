package com.application.pennypal.infrastructure.persistence.jpa.coin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_coin_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCoinAccountEntity {

    @Id
    private String userId; // same as User aggregate root ID

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalEarned;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    @PreUpdate
    protected void onUpdate(){
        this.lastUpdated = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate(){
        this.lastUpdated = LocalDateTime.now();
    }
}
