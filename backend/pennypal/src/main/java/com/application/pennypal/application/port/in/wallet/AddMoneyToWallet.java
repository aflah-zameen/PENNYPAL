package com.application.pennypal.application.port.in.wallet;

import com.application.pennypal.application.dto.input.wallet.AddWalletInputModel;

public interface AddMoneyToWallet {
    void execute(String userId,AddWalletInputModel inputModel);
}
