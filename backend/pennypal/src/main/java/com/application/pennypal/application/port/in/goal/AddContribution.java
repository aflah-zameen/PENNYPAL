package com.application.pennypal.application.port.in.goal;

import com.application.pennypal.application.dto.input.goal.AddContributionInputModel;
import com.application.pennypal.application.dto.output.goal.GoalContributionOutput;

import java.math.BigDecimal;

public interface AddContribution {
    GoalContributionOutput execute(String userId,AddContributionInputModel inputModel);
}
