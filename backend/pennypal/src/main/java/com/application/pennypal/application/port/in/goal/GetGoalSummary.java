package com.application.pennypal.application.port.in.goal;

import com.application.pennypal.application.dto.output.goal.GoalSummaryOutputModel;

public interface GetGoalSummary {
    GoalSummaryOutputModel execute(String userId);
}
