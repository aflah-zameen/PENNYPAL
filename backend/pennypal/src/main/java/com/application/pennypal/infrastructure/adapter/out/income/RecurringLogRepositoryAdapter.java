package com.application.pennypal.infrastructure.adapter.out.income;

import com.application.pennypal.application.dto.output.income.PendingIncomeSummaryOutput;
import com.application.pennypal.application.dto.output.transaction.PendingTransactionSummaryOutput;
import com.application.pennypal.application.port.out.repository.RecurringLogRepositoryPort;
import com.application.pennypal.domain.transaction.entity.RecurringTransaction;
import com.application.pennypal.domain.transaction.entity.RecurringTransactionLog;
import com.application.pennypal.domain.valueObject.RecurringLogStatus;
import com.application.pennypal.domain.valueObject.TransactionType;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.Income.PendingTransactionStatsProjection;
import com.application.pennypal.infrastructure.persistence.jpa.Income.RecurringLogRepository;
import com.application.pennypal.infrastructure.persistence.jpa.entity.RecurringTransactionLogEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.RecurringLogJpaMapper;
import com.application.pennypal.infrastructure.persistence.jpa.user.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RecurringLogRepositoryAdapter implements RecurringLogRepositoryPort {

    private final RecurringLogRepository recurringLogRepository;
    private final SpringDataUserRepository springDataUserRepository;

    @Override
    public boolean existsByRecurringIdAndDateFor(String recurringId, LocalDate dateFor) {
        return recurringLogRepository.existsByRecurringIdAndDateFor(recurringId,dateFor);
    }

    @Override
    public RecurringTransactionLog save(RecurringTransactionLog recurringLog) {
        UserEntity user = springDataUserRepository.findByUserId(recurringLog.getUserId())
                .orElseThrow(() -> new InfrastructureException("User not found","NOT_FOUND"));
        RecurringTransactionLogEntity recurringTransactionLogEntity = RecurringLogJpaMapper.toEntity(recurringLog,user);
        Optional<RecurringTransactionLogEntity> recurringTransactionLog = recurringLogRepository.findByTransactionId(recurringLog.getTransactionId());
        recurringTransactionLog.ifPresent(entity -> recurringTransactionLogEntity.setId(entity.getId()));
        RecurringTransactionLogEntity savedEntity = recurringLogRepository.save(recurringTransactionLogEntity);
        return RecurringLogJpaMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<RecurringTransactionLog> getRecurringTransactionLogByTransactionType(String userId, String transactionId, LocalDate incomeDate, TransactionType transactionType) {
        return recurringLogRepository.findOneLog(userId, transactionId, incomeDate, transactionType)
                .map(RecurringLogJpaMapper::toDomain);
    }

    @Override
    public PendingTransactionSummaryOutput getTotalPendingRecurringTransaction(String userId, LocalDate currentMonthStartDate, LocalDate currentMonthEndDate, TransactionType transactionType) {
        PendingTransactionStatsProjection pendingTransactionStatsProjection = recurringLogRepository.getPendingRecurringTransactionStats(userId,transactionType,currentMonthStartDate,currentMonthEndDate);
        return new PendingTransactionSummaryOutput(pendingTransactionStatsProjection.getTotalAmount(),pendingTransactionStatsProjection.getCount());
    }

    @Override
    public Optional<RecurringTransactionLog> getRecurringTransactionLogByIdAndUser(String userId, String recurringLogId) {
        return recurringLogRepository.findByTransactionIdAndUser_UserId(recurringLogId,userId)
                .map(RecurringLogJpaMapper::toDomain);
    }

    @Override
    public List<RecurringTransactionLog> findAllPendingRecurringLogs(String userId, TransactionType transactionType) {
        return recurringLogRepository.findAllByUser_UserIdAndTransactionTypeAndStatus(userId,transactionType, RecurringLogStatus.PENDING).stream()
                .map(RecurringLogJpaMapper::toDomain)
                .toList();
    }

//    @Override
//    public PendingTransactionSummaryOutput getTotalPendingRecurringIncome(Long userId, LocalDate startDate, LocalDate endDate) {
//       PendingIncomeStatsProjection pendingIncomeStatsProjection =  recurringIncomeLogRepository.getPendingRecurringIncomeStats(userId,startDate,endDate);
//       return new PendingTransactionSummaryOutput(pendingIncomeStatsProjection.getTotalAmount(),pendingIncomeStatsProjection.getCount());
//    }

//    @Override
//    public List<Income> findAllPendingRecurringIncomeLogs(Long userId,LocalDate date) {
//            return recurringIncomeLogRepository.findPendingIncomesByUserAndDate(userId, RecurringIncomeLogStatus.PENDING,date).stream()
//                    .map(recurringIncomeLogEntity -> {
//                        /// Set the recurring income log date in the incomeDate of IncomeEntity
//                        recurringIncomeLogEntity.getIncome().setIncomeDate(recurringIncomeLogEntity.getDate());
//                        return recurringIncomeLogEntity.getIncome();
//                    })
//                    .map(incomeJpaMapper::toDomain)
//                    .toList();
//    }

//    @Override
//    public Optional<RecurringIncomeLog> getRecurringIncomeLog(Long userId, Long incomeId, LocalDate incomeDate) {
//        Optional<RecurringIncomeLogEntity> recurringIncomeLogEntity =  recurringIncomeLogRepository.findOneLog(userId,incomeId,incomeDate);
//        Optional<RecurringIncomeLog> log = recurringIncomeLogEntity.map(recurringLogMapper::toDomain);
//        return log;
//    }

//    @Override
//    public void update(RecurringIncomeLog recurringIncomeLog) {
//        UserEntity user = springDataUserRepository.findById(recurringIncomeLog.getUserId())
//                .orElseThrow(() -> new UserNotFoundException("User not found"));
//        IncomeEntity income = incomeRepository.findById(recurringIncomeLog.getIncomeId())
//                .orElseThrow(() -> new ApplicationException("Income not found exception","NOT_FOUND"));
//        RecurringIncomeLogEntity updateRecurringLongEntity = new RecurringIncomeLogEntity(user,recurringIncomeLog.getAmount(),recurringIncomeLog.getDate(),
//                recurringIncomeLog.getStatus(),income);
//        updateRecurringLongEntity.setId(recurringIncomeLog.getId());
//        recurringIncomeLogRepository.save(updateRecurringLongEntity);
//    }
}
