package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.card.entity.Card;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CardEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;

public class CardJpaMapper {
    public static Card toDomain (CardEntity cardEntity){
        return Card.reconstruct(
                cardEntity.getCardId(),
                cardEntity.getUser().getUserId(),
                cardEntity.getName(),
                cardEntity.getHolder(),
                cardEntity.getCardNumber(),
                cardEntity.getExpiry(),
                cardEntity.getCardType(),
                cardEntity.isActive(),
                cardEntity.getBalanceAmount(),
                cardEntity.getGradient(),
                cardEntity.getHashedPin(),
                cardEntity.getCreatedAt(),
                cardEntity.getUpdatedAt()
        );
    }

    public static CardEntity toEntity(Card card, UserEntity user){
        return new CardEntity(
                card.getCardId(),
                user,
                card.getName(),
                card.getHolder(),
                card.getExpiry(),
                card.getCardNumber(),
                card.getCardType(),
                card.getBalanceAmount(),
                card.getGradient(),
                card.getHashedPin(),
                card.isActive()
        );
    }
}
