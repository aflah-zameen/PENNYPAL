package com.application.pennypal.application.service.card;

import com.application.pennypal.application.dto.output.card.PaymentMethodOutputModel;
import com.application.pennypal.application.port.in.card.GetPaymentMethods;
import com.application.pennypal.application.port.out.repository.CardRepositoryPort;
import com.application.pennypal.domain.card.entity.Card;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class GetPaymentMethodsService implements GetPaymentMethods {
    private final CardRepositoryPort cardRepositoryPort;
    @Override
    public List<PaymentMethodOutputModel> execute(String userId) {
        List<Card> cards = cardRepositoryPort.getAllCards(userId);
        return cards.stream()
                .map(card -> new PaymentMethodOutputModel(
                        card.getCardId(),
                        "card",
                        card.getName(),
                        card.getHolder(),
                        card.getBalanceAmount(),
                        card.getCardNumber(),
                        card.isActive()
                ))
                .toList();
    }
}
