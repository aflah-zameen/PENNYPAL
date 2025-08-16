package com.application.pennypal.application.port.in.goal;

import com.application.pennypal.application.dto.input.goal.EditGoalInputModel;

public interface EditGoal {
    void execute(String userId,EditGoalInputModel editGoalInputModel);
}
