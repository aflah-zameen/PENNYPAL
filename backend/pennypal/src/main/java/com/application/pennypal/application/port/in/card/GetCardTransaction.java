package com.application.pennypal.application.port.in.card;

import com.application.pennypal.application.dto.output.transaction.TransactionOutputModel;

import java.util.List;

public interface GetCardTransaction {
    List<TransactionOutputModel> getRecent(String cardId,int size);
}
