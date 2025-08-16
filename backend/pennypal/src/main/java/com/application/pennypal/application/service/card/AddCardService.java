package com.application.pennypal.application.service.card;

import com.application.pennypal.application.dto.input.card.CardInputModel;
import com.application.pennypal.application.dto.output.card.CardOutputModel;
import com.application.pennypal.application.mappers.card.CardApplicationMapper;
import com.application.pennypal.application.port.in.card.AddCard;
import com.application.pennypal.application.port.out.repository.CardRepositoryPort;
import com.application.pennypal.application.port.out.service.EncodePasswordPort;
import com.application.pennypal.domain.card.entity.Card;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddCardService implements AddCard {
    private final CardRepositoryPort cardRepositoryPort;
    private final EncodePasswordPort encodePasswordPort;
    @Override
    public CardOutputModel execute(String userId, CardInputModel cardInputModel) {
        String hashedPin = encodePasswordPort.encode(cardInputModel.pin());
        Card card = Card.create(
                userId,
                cardInputModel.name(),
                cardInputModel.holder(),
                cardInputModel.cardNumber(),
                cardInputModel.expiry(),
                cardInputModel.cardType(),
                cardInputModel.balanceAmount(),
                cardInputModel.gradient(),
                hashedPin
        );
        Card newCard = cardRepositoryPort.addCard(card);
        return CardApplicationMapper.toOutput(newCard);
    }
}
