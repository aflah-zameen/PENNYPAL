package com.application.pennypal.application.usecases.goal;

import com.application.pennypal.application.input.goal.EditGoalInputModel;

public interface EditGoal {
    void execute(Long userId,EditGoalInputModel editGoalInputModel);
}
