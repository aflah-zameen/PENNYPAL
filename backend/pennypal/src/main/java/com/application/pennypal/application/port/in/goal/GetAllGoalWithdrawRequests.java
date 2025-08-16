package com.application.pennypal.application.port.in.goal;

import com.application.pennypal.application.dto.output.goal.GoalWithdrawOutput;

import java.util.List;

public interface GetAllGoalWithdrawRequests {
    List<GoalWithdrawOutput> execute();
}
