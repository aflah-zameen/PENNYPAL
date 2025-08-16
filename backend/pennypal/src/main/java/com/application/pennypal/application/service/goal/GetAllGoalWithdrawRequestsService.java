package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.dto.output.goal.GoalWithdrawOutput;
import com.application.pennypal.application.port.in.goal.GetAllGoalWithdrawRequests;
import com.application.pennypal.application.port.out.repository.GoalWithdrawRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class GetAllGoalWithdrawRequestsService implements GetAllGoalWithdrawRequests {
    private final GoalWithdrawRepositoryPort goalWithdrawRepositoryPort;
    @Override
    public List<GoalWithdrawOutput> execute() {
        return goalWithdrawRepositoryPort.findAllGoalWithdrawRequests();
    }
}
