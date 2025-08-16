package com.application.pennypal.application.mappers.card;

import com.application.pennypal.application.dto.output.card.CardOutputModel;
import com.application.pennypal.application.dto.output.card.CardSummaryOutputModel;
import com.application.pennypal.domain.card.entity.Card;

import java.math.BigDecimal;


public class CardApplicationMapper {
    public static CardSummaryOutputModel toOutput(Card card, BigDecimal income, BigDecimal expense){
        return new CardSummaryOutputModel(
                card.getCardId(),
                card.getHolder(),
                card.getName(),
                card.getCardNumber(),
                card.getExpiry(),
                card.getCardType(),
                card.getBalanceAmount(),
                income,
                expense,
                card.getGradient(),
                card.isActive(),
                card.getCreatedAt()
        );
    }

    public static CardOutputModel toOutput(Card card){
        return new CardOutputModel(
                card.getCardId(),
                card.getName(),
                card.getCardNumber(),
                card.getExpiry(),
                card.getCardType(),
                card.getBalanceAmount(),
                card.getGradient(),
                card.isActive()
        );
    }


}
