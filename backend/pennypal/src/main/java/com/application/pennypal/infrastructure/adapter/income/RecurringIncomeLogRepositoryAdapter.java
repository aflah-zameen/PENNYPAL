package com.application.pennypal.infrastructure.adapter.income;

import com.application.pennypal.application.output.income.PendingIncomeSummaryOutput;
import com.application.pennypal.application.port.RecurringIncomeLogRepositoryPort;
import com.application.pennypal.domain.entity.Income;
import com.application.pennypal.domain.entity.RecurringIncomeLog;
import com.application.pennypal.domain.valueObject.RecurringIncomeLogStatus;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.Income.IncomeRepository;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.Income.PendingIncomeStatsProjection;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.Income.RecurringIncomeLogRepository;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.IncomeEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.RecurringIncomeLogEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper.IncomeJpaMapper;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper.RecurringIncomeLogMapper;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.user.SpringDataUserRepository;
import com.application.pennypal.shared.exception.ApplicationException;
import com.application.pennypal.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RecurringIncomeLogRepositoryAdapter implements RecurringIncomeLogRepositoryPort {

    private final RecurringIncomeLogRepository recurringIncomeLogRepository;
    private final RecurringIncomeLogMapper recurringIncomeLogMapper;
    private final SpringDataUserRepository springDataUserRepository;
    private final IncomeRepository incomeRepository;
    private final IncomeJpaMapper incomeJpaMapper;

    @Override
    public boolean existsByIncomeIdAndDate(Long incomeId, LocalDate date) {
        return recurringIncomeLogRepository.existsByIncomeIdAndDate(incomeId,date);
    }

    @Override
    public RecurringIncomeLog save(RecurringIncomeLog recurringIncomeLog) {
        UserEntity user = springDataUserRepository.findById(recurringIncomeLog.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        IncomeEntity income = incomeRepository.findById(recurringIncomeLog.getIncomeId())
                .orElseThrow(() -> new ApplicationException("Income not found exception","NOT_FOUND"));
        RecurringIncomeLogEntity recurringIncomeLogEntity  = recurringIncomeLogRepository
                .save(new RecurringIncomeLogEntity(user,recurringIncomeLog.getAmount(),recurringIncomeLog.getDate(),
                        recurringIncomeLog.getStatus(),income));
        return recurringIncomeLogMapper.toDomain(recurringIncomeLogEntity);
    }

    @Override
    public PendingIncomeSummaryOutput getTotalPendingRecurringIncome(Long userId, LocalDate startDate, LocalDate endDate) {
       PendingIncomeStatsProjection pendingIncomeStatsProjection =  recurringIncomeLogRepository.getPendingRecurringIncomeStats(userId,startDate,endDate);
       return new PendingIncomeSummaryOutput(pendingIncomeStatsProjection.getTotalAmount(),pendingIncomeStatsProjection.getCount());
    }

    @Override
    public List<Income> findAllPendingRecurringIncomeLogs(Long userId,LocalDate date) {
            return recurringIncomeLogRepository.findPendingIncomesByUserAndDate(userId, RecurringIncomeLogStatus.PENDING,date).stream()
                    .map(recurringIncomeLogEntity -> {
                        /// Set the recurring income log date in the incomeDate of IncomeEntity
                        recurringIncomeLogEntity.getIncome().setIncomeDate(recurringIncomeLogEntity.getDate());
                        return recurringIncomeLogEntity.getIncome();
                    })
                    .map(incomeJpaMapper::toDomain)
                    .toList();
    }

    @Override
    public Optional<RecurringIncomeLog> getRecurringIncomeLog(Long userId, Long incomeId, LocalDate incomeDate) {
        Optional<RecurringIncomeLogEntity> recurringIncomeLogEntity =  recurringIncomeLogRepository.findOneLog(userId,incomeId,incomeDate);
        Optional<RecurringIncomeLog> log = recurringIncomeLogEntity.map(recurringIncomeLogMapper::toDomain);
        return log;
    }

    @Override
    public void update(RecurringIncomeLog recurringIncomeLog) {
        UserEntity user = springDataUserRepository.findById(recurringIncomeLog.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        IncomeEntity income = incomeRepository.findById(recurringIncomeLog.getIncomeId())
                .orElseThrow(() -> new ApplicationException("Income not found exception","NOT_FOUND"));
        RecurringIncomeLogEntity updateRecurringLongEntity = new RecurringIncomeLogEntity(user,recurringIncomeLog.getAmount(),recurringIncomeLog.getDate(),
                recurringIncomeLog.getStatus(),income);
        updateRecurringLongEntity.setId(recurringIncomeLog.getId());
        recurringIncomeLogRepository.save(updateRecurringLongEntity);
    }
}
