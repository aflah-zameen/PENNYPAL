package com.application.pennypal.application.usecases.goal;

import com.application.pennypal.application.output.goal.GoalSummaryOutputModel;

public interface GetGoalSummary {
    GoalSummaryOutputModel execute(Long userId);
}
