package com.application.pennypal.infrastructure.adapter.out.card;

import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.out.repository.CardRepositoryPort;
import com.application.pennypal.domain.card.entity.Card;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.card.CardRepository;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CardEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.CardJpaMapper;
import com.application.pennypal.infrastructure.persistence.jpa.user.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CardRepositoryAdapter implements CardRepositoryPort {

    private final CardRepository cardRepository;
    private final SpringDataUserRepository userRepository;

    @Override
    public Optional<Card> findByCardId(String cardId) {
        return cardRepository.findByCardId(cardId)
                .map(CardJpaMapper::toDomain);
    }

    @Override
    public Optional<Card> findByUserIdAndCardId(String userId, String cardId) {
        return cardRepository.findByUser_UserIdAndCardId(userId,cardId)
                .map(CardJpaMapper::toDomain);
    }

    @Override
    public List<Card> getAllCards(String userId) {
        return cardRepository.findAllByUser_UserId(userId).stream()
                .map(CardJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Card addCard(Card card) {
        UserEntity user = userRepository.findByUserId(card.getUserId())
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));
        CardEntity entity = CardJpaMapper.toEntity(card,user);
        CardEntity newEntity =  cardRepository.save(entity);
        return CardJpaMapper.toDomain(newEntity);
    }

    @Override
    public Card update(Card card) {
        CardEntity oldCard = cardRepository.findByCardId(card.getCardId())
                .orElseThrow(() -> new InfrastructureException("Card not found for update","NOT_FOUND"));
        oldCard.setBalanceAmount(card.getBalanceAmount());
        oldCard.setHashedPin(card.getHashedPin());
        CardEntity updatedCard = cardRepository.save(oldCard);
        return CardJpaMapper.toDomain(updatedCard);
    }

}
