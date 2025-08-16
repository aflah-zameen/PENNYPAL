package com.application.pennypal.infrastructure.adapter.out.Transaction;

import com.application.pennypal.application.dto.input.transaction.TransferInputModel;
import com.application.pennypal.application.dto.output.user.TransferTransaction;
import com.application.pennypal.application.port.in.transaction.TransferMoney;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferInfrastructureService {
    private final TransferMoney transferMoney;

    @Transactional
    public TransferTransaction transfer(TransferInputModel inputModel){
        return transferMoney.execute(inputModel);
    }
}
