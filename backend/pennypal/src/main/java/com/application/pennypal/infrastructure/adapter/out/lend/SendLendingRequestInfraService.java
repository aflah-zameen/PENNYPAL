package com.application.pennypal.infrastructure.adapter.out.lend;

import com.application.pennypal.application.dto.input.lend.LendRequestInputModel;
import com.application.pennypal.application.dto.output.lend.LendingRequestOutputModel;
import com.application.pennypal.application.port.in.lend.SendLendingRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendLendingRequestInfraService {
    private final SendLendingRequest sendLendingRequest;

    @Transactional
    public LendingRequestOutputModel send(String userId, LendRequestInputModel inputModel){
        return sendLendingRequest.execute(userId,inputModel);
    }
}
