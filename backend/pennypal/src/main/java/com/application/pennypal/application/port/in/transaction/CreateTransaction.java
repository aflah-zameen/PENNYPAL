package com.application.pennypal.application.port.in.transaction;

import com.application.pennypal.application.dto.input.transaction.TransactionInputModel;
import com.application.pennypal.application.dto.output.transaction.TransactionOutputModel;

public interface CreateTransaction {
    TransactionOutputModel execute(String userId,TransactionInputModel inputModel);
}
