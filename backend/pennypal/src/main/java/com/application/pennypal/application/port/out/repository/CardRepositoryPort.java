package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.application.dto.output.card.CardSpendingOutputModel;
import com.application.pennypal.domain.card.entity.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepositoryPort {
    Optional<Card> findByCardId(String cardId);
    Optional<Card>findByUserIdAndCardId(String userId,String cardId);
    List<Card> getAllCards(String userId);
    Card addCard(Card card);
    Card update(Card card);
}

