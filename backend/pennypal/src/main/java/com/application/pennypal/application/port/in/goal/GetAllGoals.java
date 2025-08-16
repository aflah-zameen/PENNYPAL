package com.application.pennypal.application.port.in.goal;

import com.application.pennypal.application.dto.output.goal.GoalResponseOutput;

import java.util.List;

public interface GetAllGoals {
    List<GoalResponseOutput> execute(String userId);
}
