package com.application.pennypal.infrastructure.adapter.out.goal;

import com.application.pennypal.application.dto.output.goal.GoalWithdrawOutput;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.mappers.user.UserApplicationMapper;
import com.application.pennypal.application.port.out.repository.GoalWithdrawRepositoryPort;
import com.application.pennypal.domain.goal.entity.GoalWithdraw;
import com.application.pennypal.domain.valueObject.GoalWithdrawRequestStatus;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.entity.GoalWithdrawRequestEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.persistence.jpa.goal.GoalWithdrawRequestRepository;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.UserJpaMapper;
import com.application.pennypal.infrastructure.persistence.jpa.user.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GoalWithdrawRepositoryAdapter implements GoalWithdrawRepositoryPort {
    private final GoalWithdrawRequestRepository withdrawRequestRepository;
    private final SpringDataUserRepository userRepository;
    @Override
    public GoalWithdraw save(GoalWithdraw withdrawRequest) {
        GoalWithdrawRequestEntity entity = new GoalWithdrawRequestEntity(
                withdrawRequest.getId(),
                withdrawRequest.getEmail(),
                withdrawRequest.getGoalId(),
                withdrawRequest.getAmount(),
                withdrawRequest.getStatus()
        );

        GoalWithdrawRequestEntity updatedEntity =  withdrawRequestRepository.save(entity);
        return GoalWithdraw.reconstruct(
                updatedEntity.getWithdrawId(),
                updatedEntity.getEmail(),
                updatedEntity.getGoalId(),
                updatedEntity.getAmount(),
                updatedEntity.getStatus(),
                updatedEntity.getCreatedAt(),
                updatedEntity.getUpdatedAt()
        );
    }

    @Override
    public long findPendingRequestCount() {
        return withdrawRequestRepository.countByStatus(GoalWithdrawRequestStatus.PENDING);
    }

    @Override
    public Optional<GoalWithdraw> findGoalWithdrawById(String withdrawId) {
        return withdrawRequestRepository.findByWithdrawId(withdrawId)
                .map(entity -> GoalWithdraw.reconstruct(
                        entity.getWithdrawId(),
                        entity.getEmail(),
                        entity.getGoalId(),
                        entity.getAmount(),
                        entity.getStatus(),
                        entity.getCreatedAt(),
                        entity.getUpdatedAt()
                ));
    }

    @Override
    public void update(GoalWithdraw goalWithdraw) {
        GoalWithdrawRequestEntity entity = withdrawRequestRepository.findByWithdrawId(goalWithdraw.getId())
                .orElseThrow(() -> new InfrastructureException("Withdraw jpa entity cannot be found","NOT_FOUND"));
        entity.setStatus(goalWithdraw.getStatus());
        withdrawRequestRepository.save(entity);
    }

    @Override
    public List<GoalWithdrawOutput> findAllGoalWithdrawRequests() {
        return withdrawRequestRepository.findAllByStatus(GoalWithdrawRequestStatus.PENDING)
                .stream()
                .map(entity -> {
                    UserEntity user =  userRepository.findByEmail(entity.getEmail())
                        .orElseThrow(() -> new InfrastructureException("User not found","NOT_FOUND"));
                    return new GoalWithdrawOutput(
                            entity.getWithdrawId(),
                            entity.getGoalId(),
                            UserApplicationMapper.toOutput(UserJpaMapper.toDomain(user)),
                            entity.getAmount(),
                            entity.getCreatedAt(),
                            entity.getStatus().getValue()
                );
                }).toList();
    }


}
