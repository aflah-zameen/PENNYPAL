package com.application.pennypal.application.port.in.goal;

import com.application.pennypal.application.dto.input.goal.AddGoalInputModel;
import com.application.pennypal.application.dto.output.goal.GoalResponseOutput;

public interface AddGoal {
    GoalResponseOutput execute(AddGoalInputModel goalInputModel,String userId);
}
