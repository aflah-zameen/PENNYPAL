package com.application.pennypal.interfaces.rest.mappers;

import com.application.pennypal.application.dto.output.card.CardOutputModel;
import com.application.pennypal.application.dto.output.card.CardSummaryOutputModel;
import com.application.pennypal.interfaces.rest.dtos.card.CardSummaryResponseDTO;
import com.application.pennypal.interfaces.rest.dtos.card.CardUserResponseDTO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CardDtoMapper {
    public static CardSummaryResponseDTO toResponse(CardSummaryOutputModel outputModel){
        return new CardSummaryResponseDTO(
                outputModel.cardId() != null ?outputModel.cardId():null,
                outputModel.cardNumber().substring(outputModel.cardNumber().length() - 4),
                outputModel.cardName(),
                formatToMMYY(outputModel.expiry()),
                outputModel.userName(),
                outputModel.cardType().getValue(),
                outputModel.balanceAmount(),
                outputModel.income(),
                outputModel.expense(),
                outputModel.gradient(),
                outputModel.active(),
                outputModel.createdAt()
        );
    }

    public static CardUserResponseDTO toResponse(CardOutputModel outputModel){
        return new CardUserResponseDTO(
                outputModel.cardId(),
                outputModel.cardName(),
                outputModel.cardNumber(),
                formatToMMYY(outputModel.expiry()),
                outputModel.cardType().getValue(),
                outputModel.balanceAmount(),
                outputModel.gradient(),
                outputModel.active()
                );
    }


    private static String formatToMMYY(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        return date.format(formatter);
    }
}
