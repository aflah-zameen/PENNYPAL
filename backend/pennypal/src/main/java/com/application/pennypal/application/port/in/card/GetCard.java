package com.application.pennypal.application.port.in.card;

import com.application.pennypal.application.dto.output.card.CardOutputModel;
import com.application.pennypal.application.dto.output.card.CardSummaryOutputModel;

import java.util.List;

public interface GetCard {
    List<CardSummaryOutputModel> getAllSummary(String userId);
    CardOutputModel getById(String userId,String cardId);
    List<CardOutputModel> getAll(String userId);
}
