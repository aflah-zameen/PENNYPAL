package com.application.pennypal.infrastructure.adapter.out.wallet;

import com.application.pennypal.application.dto.input.wallet.AddWalletInputModel;
import com.application.pennypal.application.port.in.wallet.AddMoneyToWallet;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddMoneyWalletInfraService {
    private final AddMoneyToWallet addMoneyToWallet;

    @Transactional
    public void setAddMoneyToWallet(String userId, AddWalletInputModel inputModel){
        addMoneyToWallet.execute(userId,inputModel);
    }
}
