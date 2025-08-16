package com.application.pennypal.application.port.in.transaction;

import com.application.pennypal.application.dto.input.transaction.TransferInputModel;
import com.application.pennypal.application.dto.output.user.TransferTransaction;

public interface TransferMoney {
    TransferTransaction execute(TransferInputModel inputModel);
}
