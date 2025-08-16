package com.application.pennypal.infrastructure.adapter.out.goal;

import com.application.pennypal.application.port.in.goal.GoalWithdrawalApproval;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoalWithdrawalApprovalInfraService {
    private final GoalWithdrawalApproval goalWithdrawalApproval;

    @Transactional
    public void approveWithdrawal(String withdrawId) {
        goalWithdrawalApproval.execute(withdrawId);
    }
}
