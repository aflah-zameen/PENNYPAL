package com.application.pennypal.infrastructure.adapter.out.goal;

import com.application.pennypal.application.port.in.goal.GoalWithdrawalApproval;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoalWithdrawApprovalInfraService {
    private final GoalWithdrawalApproval goalWithdrawalApproval;

    @Transactional
    public void approveWithdrawRequest(String requestId) {
        goalWithdrawalApproval.execute(requestId);
    }
}
