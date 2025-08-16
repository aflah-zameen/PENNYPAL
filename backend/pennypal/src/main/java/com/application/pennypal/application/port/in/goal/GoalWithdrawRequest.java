package com.application.pennypal.application.port.in.goal;


import com.application.pennypal.application.dto.input.goal.GoalWithdrawRequestInputModel;

public interface GoalWithdrawRequest {
    void execute(GoalWithdrawRequestInputModel inputModel);
}
