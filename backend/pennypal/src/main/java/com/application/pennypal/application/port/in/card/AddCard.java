package com.application.pennypal.application.port.in.card;

import com.application.pennypal.application.dto.input.card.CardInputModel;
import com.application.pennypal.application.dto.output.card.CardOutputModel;

public interface AddCard {
    CardOutputModel execute(String userId, CardInputModel cardInputModel);
}
