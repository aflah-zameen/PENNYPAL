package com.application.pennypal.interfaces.rest.mappers;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.dto.input.goal.AddGoalInputModel;
import com.application.pennypal.application.dto.input.goal.EditGoalInputModel;
import com.application.pennypal.application.dto.output.goal.GoalResponseOutput;
import com.application.pennypal.interfaces.rest.dtos.goal.AddGoalRequestDTO;
import com.application.pennypal.interfaces.rest.dtos.goal.EditGoalRequestDTO;
import com.application.pennypal.interfaces.rest.dtos.goal.GoalContributionResponseDTO;
import com.application.pennypal.interfaces.rest.dtos.goal.GoalResponseDTO;

import java.time.LocalDate;

public class GoalDtoMapper {
    public static GoalResponseDTO toDto (GoalResponseOutput goalResponseOutput){
        return new GoalResponseDTO(
                goalResponseOutput.goalId(),
                goalResponseOutput.userId(),
                goalResponseOutput.title(),
                goalResponseOutput.description(),
                goalResponseOutput.targetAmount(),
                goalResponseOutput.currentAmount(),
                goalResponseOutput.startDate(),
                goalResponseOutput.endDate(),
                goalResponseOutput.status().getValue(),
                CategoryDtoMapper.toResponse(goalResponseOutput.category()),
                goalResponseOutput.priorityLevel(),
                goalResponseOutput.deleted(),
                goalResponseOutput.createdAt(),
                goalResponseOutput.updatedAt(),
                goalResponseOutput.contributions().stream()
                        .map(contribution -> new GoalContributionResponseDTO(contribution.contributionId(),contribution.amount(),contribution.date(),contribution.notes(),contribution.coins()))
                        .toList()
        );
    }

    public static AddGoalInputModel toInput(AddGoalRequestDTO addGoalRequestDTO){
        if(addGoalRequestDTO.startDate() != null && (LocalDate.parse(addGoalRequestDTO.startDate())).isBefore(LocalDate.now())){
            throw new ApplicationBusinessException("Start date cannot be before current date","DATE_VALIDATION_ERROR");
        }
        if(addGoalRequestDTO.startDate() != null && addGoalRequestDTO.endDate() != null && LocalDate.parse(addGoalRequestDTO.startDate()).isAfter(LocalDate.parse(addGoalRequestDTO.endDate()))){
            throw new ApplicationBusinessException("Start date cannot be after end date","DATE_VALIDATION_ERROR");
        }
        return new AddGoalInputModel(
                addGoalRequestDTO.title(),
                addGoalRequestDTO.description(),
                addGoalRequestDTO.targetAmount(),
                addGoalRequestDTO.startDate() != null ? LocalDate.parse(addGoalRequestDTO.startDate()) : null,
                addGoalRequestDTO.endDate() != null ? LocalDate.parse(addGoalRequestDTO.endDate()) : null,
                addGoalRequestDTO.priorityLevel() == null ? 0 : addGoalRequestDTO.priorityLevel(),
                addGoalRequestDTO.categoryId()
        );
    }

    public static EditGoalInputModel toInput(EditGoalRequestDTO editGoalRequestDTO){
        if(editGoalRequestDTO.startDate() != null && (LocalDate.parse(editGoalRequestDTO.startDate())).isBefore(LocalDate.now())){
            throw new ApplicationBusinessException("Start date cannot be before current date","DATE_VALIDATION_ERROR");
        }
        if(editGoalRequestDTO.startDate() != null && editGoalRequestDTO.endDate() != null && LocalDate.parse(editGoalRequestDTO.startDate()).isAfter(LocalDate.parse(editGoalRequestDTO.endDate()))){
            throw new ApplicationBusinessException("Start date cannot be after end date","DATE_VALIDATION_ERROR");
        }
        return new EditGoalInputModel(
                editGoalRequestDTO.goalId(),
                editGoalRequestDTO.title(),
                editGoalRequestDTO.description(),
                editGoalRequestDTO.targetAmount(),
                editGoalRequestDTO.startDate() != null ? LocalDate.parse(editGoalRequestDTO.startDate()) : null,
                editGoalRequestDTO.endDate() != null ? LocalDate.parse(editGoalRequestDTO.endDate()) : null,
                editGoalRequestDTO.priorityLevel() == null ? 0 : editGoalRequestDTO.priorityLevel(),
                editGoalRequestDTO.categoryId()
        );
    }
}
