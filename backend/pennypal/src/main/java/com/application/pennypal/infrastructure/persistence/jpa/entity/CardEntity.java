package com.application.pennypal.infrastructure.persistence.jpa.entity;

import com.application.pennypal.domain.card.valueObject.CardType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
@Getter
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,updatable = false,unique = true)
    private String cardId;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "userId",nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String holder;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private LocalDate expiry;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType;

    @Setter
    @Column(nullable = false)
    private BigDecimal balanceAmount;

    @Column(nullable = false)
    private String gradient;

    @Setter
    @Column(nullable = false)
    private String hashedPin;

    @Column(nullable = false)
    private boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CardEntity(
            String cardId,
            UserEntity user,
            String name,
            String holder,
            LocalDate expiry,
            String cardNumber,
            CardType cardType,
            BigDecimal balanceAmount,
            String gradient,
            String hashedPin,
            boolean active
    ){
        this.cardId = cardId;
        this.user = user;
        this.name = name;
        this.holder = holder;
        this.expiry = expiry;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.balanceAmount = balanceAmount;
        this.gradient = gradient;
        this.hashedPin = hashedPin;
        this.active = active;
    }

    protected CardEntity(){}

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected  void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

}
