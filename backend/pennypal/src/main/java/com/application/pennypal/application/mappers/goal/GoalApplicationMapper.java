package com.application.pennypal.application.mappers.goal;

import com.application.pennypal.application.exception.BusinessException;
import com.application.pennypal.application.input.goal.AddGoalInputModel;
import com.application.pennypal.application.input.goal.EditGoalInputModel;
import com.application.pennypal.application.mappers.category.CategoryApplicationMapper;
import com.application.pennypal.application.output.goal.GoalContributionOutput;
import com.application.pennypal.application.output.goal.GoalResponseOutput;
import com.application.pennypal.application.port.CategoryManagementRepositoryPort;
import com.application.pennypal.domain.entity.Category;
import com.application.pennypal.domain.entity.Goal;
import com.application.pennypal.domain.entity.Income;
import com.application.pennypal.domain.entity.Transactions;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GoalApplicationMapper {
    private final CategoryManagementRepositoryPort categoryManagementRepositoryPort;
    public  GoalResponseOutput toOutput(Goal goal, List<Transactions> transactionsList){
        Category category = categoryManagementRepositoryPort.findById(goal.getCategoryId())
                .orElseThrow(()->new BusinessException("Category not found","NOT_FOUND"));
        return new GoalResponseOutput(
                goal.getId(),
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
                transactionsList.stream()
                        .map(trx -> new GoalContributionOutput(trx.getId(),trx.getAmount(),trx.getTransactionDate(),trx.getDescription()))
                        .toList()
        );
    }

    public Goal toDomain(AddGoalInputModel addGoalInputModel,Long userId){
        return new Goal(
                null,
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
    public Goal toDomain(EditGoalInputModel editGoalInputModel, Long userId){
        return new Goal(
                editGoalInputModel.id(),
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
