package com.application.pennypal.infrastructure.adapter.goal;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.GoalRepositoryPort;
import com.application.pennypal.domain.entity.Goal;
import com.application.pennypal.domain.valueObject.GoalStatus;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.category.CategoryRepository;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.GoalEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.goal.GoalRepository;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper.GoalJpaMapper;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.user.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GoalRepositoryAdapter implements GoalRepositoryPort {
    private final GoalRepository goalRepository;
    private final GoalJpaMapper goalJpaMapper;
    private final CategoryRepository categoryRepository;
    private final SpringDataUserRepository springDataUserRepository;
    @Override
    public Goal save(Goal goal) {
        UserEntity user = springDataUserRepository.findById(goal.getUserId())
                .orElseThrow(() -> new ApplicationBusinessException("User not found","NOT_FOUND"));
        CategoryEntity category = categoryRepository.findById(goal.getCategoryId())
                .orElseThrow(() -> new ApplicationBusinessException("Category not found","NOT_FOUND"));
        GoalEntity goalEntity = goalJpaMapper.toEntity(goal,user,category);
        GoalEntity responseGoalEntity = goalRepository.save(goalEntity);
        return goalJpaMapper.toDomain(responseGoalEntity);
    }

    @Override
    public List<Goal> getAllNonDeletedGoals(Long userId) {
        List<GoalEntity> goalEntities = goalRepository.findAllByUserIdAndDeletedFalseOrderByCreatedAtDesc(userId);
        return goalEntities.stream()
                .map(goalJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Goal> getGoalById(Long goalId) {
        return goalRepository.findById(goalId).map(goalJpaMapper::toDomain);
    }

    @Override
    public Long getTotalGoalsByUserIdAndStatus(Long userId, GoalStatus goalStatus) {
        return goalRepository.countByUserIdAndStatusAndDeletedFalse(userId,goalStatus);
    }

    @Override
    public BigDecimal getTotalAmountSaved(Long userId) {
        return goalRepository.getTotalSavedByUserId(userId);
    }
}
