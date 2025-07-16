package com.application.pennypal.application.usecases.goal;

import com.application.pennypal.application.output.goal.GoalResponseOutput;

import java.util.List;

public interface GetAllGoals {
    List<GoalResponseOutput> execute(Long userId);
}
