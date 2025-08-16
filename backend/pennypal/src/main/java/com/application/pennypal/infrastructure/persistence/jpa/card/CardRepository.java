package com.application.pennypal.infrastructure.persistence.jpa.card;

import com.application.pennypal.domain.card.entity.Card;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity,Long> {
    Optional<CardEntity> findByCardId(String cardId);

    Optional<CardEntity> findByUser_UserIdAndCardId(String userId, String cardId);

    List<CardEntity> findAllByUser_UserId(String userId);

}
