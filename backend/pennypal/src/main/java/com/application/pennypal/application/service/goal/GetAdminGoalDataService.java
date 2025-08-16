package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.dto.input.goal.GoalAdminFilter;
import com.application.pennypal.application.dto.output.goal.AdminGoalResponseOutput;
import com.application.pennypal.application.dto.output.paged.PagedResultOutput;
import com.application.pennypal.application.port.in.goal.GetAdminGoalData;
import com.application.pennypal.application.port.out.repository.GoalRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAdminGoalDataService implements GetAdminGoalData {
    private final GoalRepositoryPort goalRepositoryPort;

    @Override
    public PagedResultOutput<AdminGoalResponseOutput> execute(GoalAdminFilter adminFilter, int page, int size) {
        return goalRepositoryPort.fetchFilteredAdminGoals(adminFilter,page,size);
    }
}
