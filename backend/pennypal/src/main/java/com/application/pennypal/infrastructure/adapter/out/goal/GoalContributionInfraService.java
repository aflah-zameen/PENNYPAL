package com.application.pennypal.infrastructure.adapter.out.goal;

import com.application.pennypal.application.dto.input.goal.AddContributionInputModel;
import com.application.pennypal.application.dto.output.goal.GoalContributionOutput;
import com.application.pennypal.application.port.in.goal.AddContribution;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoalContributionInfraService {
    private final AddContribution addContribution;

    @Transactional
    public GoalContributionOutput addContribution(String userId, AddContributionInputModel inputModel){
        return addContribution.execute(userId,inputModel);
    }
}
