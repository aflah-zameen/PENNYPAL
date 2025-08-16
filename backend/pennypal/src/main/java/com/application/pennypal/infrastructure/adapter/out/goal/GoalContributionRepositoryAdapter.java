package com.application.pennypal.infrastructure.adapter.out.goal;

import com.application.pennypal.application.port.out.repository.GoalContributionRepositoryPort;
import com.application.pennypal.domain.goal.entity.GoalContribution;
import com.application.pennypal.infrastructure.persistence.jpa.entity.GoalContributionEntity;
import com.application.pennypal.infrastructure.persistence.jpa.goal.GoalContributionRepository;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.GoalContributionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GoalContributionRepositoryAdapter implements GoalContributionRepositoryPort {
    final private GoalContributionRepository goalContributionRepository;

    @Override
    public GoalContribution save(GoalContribution contribution) {
        GoalContributionEntity entity = GoalContributionMapper.toEntity(contribution);
        GoalContributionEntity newEntity = goalContributionRepository.save(entity);
        return GoalContributionMapper.toDomain(newEntity);
    }

    @Override
    public List<GoalContribution> getAllContributions(String userId,String goalId){
        return goalContributionRepository.findAllByUserIdAndGoalId(userId,goalId).stream()
                .map(GoalContributionMapper::toDomain)
                .toList();
    }
}
