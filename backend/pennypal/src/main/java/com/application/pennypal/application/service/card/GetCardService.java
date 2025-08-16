package com.application.pennypal.application.service.card;

import com.application.pennypal.application.dto.output.card.CardOutputModel;
import com.application.pennypal.application.dto.output.card.CardSummaryOutputModel;
import com.application.pennypal.application.exception.ApplicationErrorCode;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.mappers.card.CardApplicationMapper;
import com.application.pennypal.application.port.in.card.GetCard;
import com.application.pennypal.application.port.out.repository.CardRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.domain.card.entity.Card;
import com.application.pennypal.domain.valueObject.TransactionType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@RequiredArgsConstructor
public class GetCardService implements GetCard {
    private final CardRepositoryPort cardRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;

    @Override
    public List<CardSummaryOutputModel> getAllSummary(String userId) {
        List<Card> cards = cardRepositoryPort.getAllCards(userId);
        return cards.stream().map(card -> {
            BigDecimal income = transactionRepositoryPort.getTotalAmountByCardAndType(card.getCardId(), TransactionType.INCOME);
            BigDecimal expense = transactionRepositoryPort.getTotalAmountByCardAndType(card.getCardId(),TransactionType.EXPENSE);
            return CardApplicationMapper.toOutput(card,income,expense);
        }).toList();
    }

    @Override
    public CardOutputModel getById(String userId, String cardId) {
        Card card = cardRepositoryPort.findByUserIdAndCardId(userId,cardId)
                .orElseThrow(() -> new ApplicationBusinessException("Card is not found", ApplicationErrorCode.CARD_NOT_FOUND.getErrorCode()));
        return CardApplicationMapper.toOutput(card);
    }

    @Override
    public List<CardOutputModel> getAll(String userId) {
        List<Card> cards = cardRepositoryPort.getAllCards(userId);
        return cards.stream().map(CardApplicationMapper::toOutput).toList();
    }
}
