package com.application.pennypal.application.port.in.goal;

import com.application.pennypal.application.dto.input.goal.GoalAdminFilter;
import com.application.pennypal.application.dto.output.goal.AdminGoalResponseOutput;
import com.application.pennypal.application.dto.output.paged.PagedResultOutput;

public interface GetAdminGoalData {
    PagedResultOutput<AdminGoalResponseOutput> execute(GoalAdminFilter adminFilter, int page, int size);
}
