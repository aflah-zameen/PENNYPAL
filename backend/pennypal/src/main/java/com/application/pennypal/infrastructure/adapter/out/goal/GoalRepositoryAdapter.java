package com.application.pennypal.infrastructure.adapter.out.goal;

import com.application.pennypal.application.dto.input.goal.GoalAdminFilter;
import com.application.pennypal.application.dto.output.goal.AdminGoalResponseOutput;
import com.application.pennypal.application.dto.output.goal.AdminGoalStats;
import com.application.pennypal.application.dto.output.goal.GoalResponseOutput;
import com.application.pennypal.application.dto.output.paged.PagedResultOutput;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.mappers.category.CategoryApplicationMapper;
import com.application.pennypal.application.mappers.user.UserApplicationMapper;
import com.application.pennypal.application.port.out.repository.GoalRepositoryPort;
import com.application.pennypal.domain.goal.entity.Goal;
import com.application.pennypal.domain.valueObject.GoalStatus;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.category.CategoryRepository;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.GoalEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.persistence.jpa.goal.GoalRepository;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.GoalJpaMapper;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.UserJpaMapper;
import com.application.pennypal.infrastructure.persistence.jpa.specification.GoalSpecification;
import com.application.pennypal.infrastructure.persistence.jpa.user.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GoalRepositoryAdapter implements GoalRepositoryPort {
    private final GoalRepository goalRepository;
    private final CategoryRepository categoryRepository;
    private final SpringDataUserRepository springDataUserRepository;
    @Override
    public Goal save(Goal goal) {
        UserEntity user = springDataUserRepository.findByUserId(goal.getUserId())
                .orElseThrow(() -> new ApplicationBusinessException("User not found","NOT_FOUND"));
        CategoryEntity category = categoryRepository.findByCategoryId(goal.getCategoryId())
                .orElseThrow(() -> new ApplicationBusinessException("Category not found","NOT_FOUND"));
        GoalEntity goalEntity = GoalJpaMapper.toEntity(goal,user,category);
        GoalEntity responseGoalEntity = goalRepository.save(goalEntity);
        return GoalJpaMapper.toDomain(responseGoalEntity);
    }

    @Override
    public Goal update(Goal updatedGoal,String goalId) {
        GoalEntity goalEntity = goalRepository.findByGoalId(goalId)
                .orElseThrow(() -> new InfrastructureException("Goal entity not found","NOT_FOUND"));

        CategoryEntity category = categoryRepository.findByCategoryId(updatedGoal.getCategoryId())
                        .orElseThrow(()-> new InfrastructureException("Category entity cannot be found","NOT_FOUND"));

        goalEntity.setCurrentAmount(updatedGoal.getCurrentAmount());
        goalEntity.setTitle(updatedGoal.getTitle());
        goalEntity.setDescription(updatedGoal.getDescription());
        goalEntity.setStartDate(updatedGoal.getStartDate());
        goalEntity.setEndDate(updatedGoal.getEndDate());
        goalEntity.setPriorityLevel(updatedGoal.getPriorityLevel());
        goalEntity.setDeleted(updatedGoal.isDeleted());
        goalEntity.setTargetAmount(updatedGoal.getTargetAmount());
        goalEntity.setStatus(updatedGoal.getStatus());
        goalEntity.setCategory(category);

        GoalEntity newGoal = goalRepository.save(goalEntity);
        return GoalJpaMapper.toDomain(newGoal);
    }

    @Override
    public List<Goal> getAllNonDeletedGoals(String userId) {
        List<GoalEntity> goalEntities = goalRepository.findAllByUser_UserIdAndDeletedFalseOrderByCreatedAtDesc(userId);
        return goalEntities.stream()
                .map(GoalJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Goal> getGoalById(String goalId) {
        return goalRepository.findByGoalId(goalId).map(GoalJpaMapper::toDomain);
    }

    @Override
    public Long getTotalGoalsByUserIdAndStatus(String userId, GoalStatus goalStatus) {
        return goalRepository.countByUser_UserIdAndStatusAndDeletedFalse(userId,goalStatus);
    }

    @Override
    public BigDecimal getTotalAmountSaved(String userId) {
        return goalRepository.getTotalSavedByUserId(userId);
    }

    @Override
    public PagedResultOutput<AdminGoalResponseOutput> fetchFilteredAdminGoals(GoalAdminFilter adminFilter, int page, int size) {
        Specification<GoalEntity> spec = GoalSpecification.fromFilter(adminFilter);
         Pageable pageable = PageRequest.of(page,size);
         Page<GoalEntity> goalEntities = goalRepository.findAll(spec,pageable);
         List<AdminGoalResponseOutput> responseOutputs = goalEntities.getContent().stream()
                 .map(entity -> new AdminGoalResponseOutput(
                         entity.getGoalId(),
                         UserApplicationMapper.toOutput(UserJpaMapper.toDomain(entity.getUser())),
                         entity.getTitle(),
                         entity.getDescription(),
                         entity.getTargetAmount(),
                         entity.getCurrentAmount(),
                         entity.getStartDate(),
                         entity.getEndDate(),
                         entity.getUpdatedAt(),
                         entity.getStatus(),
                         entity.getCategory().getName(),
                         entity.getCreatedAt(),
                         entity.getUpdatedAt()
                 ))
                 .toList();
         return new PagedResultOutput<>(
                 responseOutputs,
                 goalEntities.getNumber(),
                 goalEntities.getSize(),
                 goalEntities.getTotalElements(),
                 goalEntities.getTotalPages()
         );
    }

    @Override
    public AdminGoalStats getAdminGoalStats() {
        Object[] stats = goalRepository.fetchGoalStats();
        long totalGoals = 0;
        long activeGoals = 0;
        long completedGoals = 0;
        BigDecimal totalContribution = BigDecimal.ZERO;

        if (stats != null && stats.length == 1 && stats[0] instanceof Object[]) {
            Object[] row = (Object[]) stats[0];
            if (row.length >= 4) {
                totalGoals = row[0] instanceof Number ? ((Number) row[0]).longValue() : 0;
                activeGoals = row[1] instanceof Number ? ((Number) row[1]).longValue() : 0;
                completedGoals = row[2] instanceof Number ? ((Number) row[2]).longValue() : 0;
                totalContribution = row[3] instanceof BigDecimal ? (BigDecimal) row[3] : BigDecimal.ZERO;
            }
        }
        return new AdminGoalStats(totalGoals,activeGoals,completedGoals,totalContribution,0);
    }
}
