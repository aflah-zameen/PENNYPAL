package com.application.pennypal.domain.wallet.entity;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.NegativeAmountNotAllowedExceptionDomain;
import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Wallet {
    private final String walletId;
    private final String userId;
    private BigDecimal balanceAmount;
    private boolean active;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Wallet(
            String walletId,
            String userId,
            BigDecimal balanceAmount,
            boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        this.walletId = walletId;
        this.userId =userId;
        this.balanceAmount = balanceAmount;
        this.active =active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /// Factory method to create
    public static Wallet create(
            String userId,
            BigDecimal balanceAmount
    ){
        String walletId = "WAL_"+ UUID.randomUUID();
        return new Wallet(
                walletId,
                userId,
                balanceAmount,
                true,
                null,
                null
        );
    }

    /// Factory method to reconstruct
    public static Wallet reconstruct(
            String walletId,
            String userId,
            BigDecimal balanceAmount,
            boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        return new Wallet(
                walletId,
                userId,
                balanceAmount,
                active,
                createdAt,
                updatedAt
        );
    }

    /// Business methods

    /// Credit amount
    public Wallet creditAmount(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new NegativeAmountNotAllowedExceptionDomain();
        }
        this.balanceAmount = this.balanceAmount.add(amount);
        return this;
    }

    /// Debit amount
    public Wallet debitAmount(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new NegativeAmountNotAllowedExceptionDomain();
        }

        if(amount.compareTo(BigDecimal.ZERO) == 0){
            throw new DomainBusinessException("Insufficient balance", DomainErrorCode.INSUFFICIENT_BALANCE);
        }

        // Update balance amount
        BigDecimal temp = this.balanceAmount.subtract(amount);
        if(temp.compareTo(BigDecimal.ZERO) < 0){
            throw new NegativeAmountNotAllowedExceptionDomain();
        }
        this.balanceAmount = temp;

        return this;
    }


    public Wallet activate(){
        this.active = true;
        return this;
    }

    public Wallet inactivate(){
        this.active = false;
        return this;
    }
}
