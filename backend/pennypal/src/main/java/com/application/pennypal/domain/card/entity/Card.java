package com.application.pennypal.domain.card.entity;

import com.application.pennypal.domain.card.valueObject.CardType;
import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.NegativeAmountNotAllowedExceptionDomain;
import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Card {
    private final String cardId;
    private final String userId;
    private final String name;
    private final String holder;
    private final String cardNumber;
    private final LocalDate expiry;
    private final CardType cardType;
    private BigDecimal balanceAmount;
    private final String gradient;
    private String hashedPin;
    private boolean active;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Card(
            String cardId,
            String userId,
            String name,
            String holder,
            String cardNumber,
            LocalDate expiry,
            CardType cardType,
            BigDecimal balanceAmount,
            String gradient,
            boolean active,
            String hashedPin,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        this.cardId = cardId;
        this.userId = userId;
        this.name = name;
        this.holder = holder;
        this.cardNumber = cardNumber;
        this.expiry = expiry;
        this.cardType = cardType;
        this.balanceAmount = balanceAmount;
        this.gradient = gradient;
        this.active = active;
        this.hashedPin = hashedPin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /// Factory method to create
    public static Card create(
            String userId,
            String name,
            String holder,
            String cardNumber,
            LocalDate expiry,
            CardType cardType,
            BigDecimal balanceAmount,
            String gradient,
            String hashedPin
    ){
        String cardId = "CARD_"+ UUID.randomUUID();
        return new Card(
                cardId,
                userId,
                name,
                holder,
                cardNumber,
                expiry,
                cardType,
                balanceAmount,
                gradient,
                true,
                hashedPin,
                null,
                null
        );
    }

    /// Factory method to reconstruct
    public static Card reconstruct(
            String cardId,
            String userId,
            String name,
            String holder,
            String cardNumber,
            LocalDate expiry,
            CardType cardType,
            boolean active,
            BigDecimal balanceAmount,
            String gradient,
            String hashedPin,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        return new Card(
                cardId,
                userId,
                name,
                holder,
                cardNumber,
                expiry,
                cardType,
                balanceAmount,
                gradient,
                active,
                hashedPin,
                createdAt,
                updatedAt
        );
    }

    /// Business methods
    public Card creditAmount(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new NegativeAmountNotAllowedExceptionDomain();
        }

        /// Update balance amount
        this.balanceAmount = this.balanceAmount.add(amount);

        return this;
    }

    public Card debitAmount(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new NegativeAmountNotAllowedExceptionDomain();
        }

        if(this.balanceAmount.compareTo(amount) < 0){
            throw new DomainBusinessException("Insufficient balance", DomainErrorCode.INSUFFICIENT_BALANCE);
        }

        /// Update balance amount
        BigDecimal temp = this.balanceAmount.subtract(amount);
        if(temp.compareTo(BigDecimal.ZERO) < 0){
            throw new NegativeAmountNotAllowedExceptionDomain();
        }
        this.balanceAmount = temp;

        return this;
    }

    public Card toggleCardStatus(){
        this.active = !this.active;
        return this;
    }

    public boolean isCardValid(){
        return LocalDate.now().isBefore(expiry);
    }

    public Card updatePin(String hashedPin){
        this.hashedPin = hashedPin;
        return this;
    }
}
