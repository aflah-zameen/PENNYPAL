package com.application.pennypal.application.mappers.goal;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.dto.input.goal.AddGoalInputModel;
import com.application.pennypal.application.dto.input.goal.EditGoalInputModel;
import com.application.pennypal.application.mappers.category.CategoryApplicationMapper;
import com.application.pennypal.application.dto.output.goal.GoalContributionOutput;
import com.application.pennypal.application.dto.output.goal.GoalResponseOutput;
import com.application.pennypal.application.port.out.repository.CategoryManagementRepositoryPort;
import com.application.pennypal.domain.catgeory.entity.Category;
import com.application.pennypal.domain.goal.entity.Goal;
import com.application.pennypal.domain.goal.entity.GoalContribution;
import com.application.pennypal.domain.transaction.entity.Transaction;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GoalApplicationMapper {
    private final CategoryManagementRepositoryPort categoryManagementRepositoryPort;
    private final GoalContributionApplicationMapper goalContributionApplicationMapper;
    public  GoalResponseOutput toOutput(Goal goal, List<GoalContribution> contributionList){
        Category category = categoryManagementRepositoryPort.findByCategoryId(goal.getCategoryId())
                .orElseThrow(()->new ApplicationBusinessException("Category not found","NOT_FOUND"));
        return new GoalResponseOutput(
                goal.getGoalId(),
                goal.getUserId(),
                goal.getTitle(),
                goal.getDescription(),
                goal.getTargetAmount(),
                goal.getCurrentAmount(),
                goal.getStartDate(),
                goal.getEndDate(),
                goal.getStatus(),
                CategoryApplicationMapper.toOutput(category),
                goal.getPriorityLevel(),
                goal.isDeleted(),
                goal.getCreatedAt(),
                goal.getUpdatedAt(),
                contributionList.stream()
                        .map(goalContributionApplicationMapper::toOutput)
                        .toList()
        );
    }

    public Goal toDomain(AddGoalInputModel addGoalInputModel,String userId){
        return Goal.create(
                userId,
                addGoalInputModel.title(),
                addGoalInputModel.targetAmount(),
                addGoalInputModel.startDate(),
                addGoalInputModel.endDate(),
                addGoalInputModel.categoryId(),
                addGoalInputModel.priorityLevel(),
                addGoalInputModel.description()
        );
    }
    public Goal toDomain(EditGoalInputModel editGoalInputModel, String userId){
        return Goal.create(
                userId,
                editGoalInputModel.title(),
                editGoalInputModel.targetAmount(),
                editGoalInputModel.startDate(),
                editGoalInputModel.endDate(),
                editGoalInputModel.categoryId(),
                editGoalInputModel.priorityLevel(),
                editGoalInputModel.description()
        );
    }
}
