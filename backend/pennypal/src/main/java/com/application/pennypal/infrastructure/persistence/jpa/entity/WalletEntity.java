package com.application.pennypal.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
@Getter
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,updatable = false)
    private String walletId;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private UserEntity user;

    @Setter
    @Column(nullable = false)
    private BigDecimal balanceAmount;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public WalletEntity(
            String walletId,
            UserEntity user,
            BigDecimal balanceAmount,
            boolean active
    ){
        this.walletId = walletId;
        this.user = user;
        this.balanceAmount = balanceAmount;
        this.active = active;
    }

    protected WalletEntity(){}

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
