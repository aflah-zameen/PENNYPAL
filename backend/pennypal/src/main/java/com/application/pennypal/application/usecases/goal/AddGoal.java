package com.application.pennypal.application.usecases.goal;

import com.application.pennypal.application.input.goal.AddGoalInputModel;
import com.application.pennypal.application.output.goal.GoalResponseOutput;

public interface AddGoal {
    GoalResponseOutput execute(AddGoalInputModel goalInputModel,Long userId);
}
