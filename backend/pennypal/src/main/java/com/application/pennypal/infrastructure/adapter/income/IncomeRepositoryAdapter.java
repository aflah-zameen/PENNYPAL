package com.application.pennypal.infrastructure.adapter.income;


import com.application.pennypal.application.mappers.category.CategoryApplicationMapper;
import com.application.pennypal.application.output.income.PendingIncomeSummaryOutput;
import com.application.pennypal.application.output.income.RecurringIncomeOutput;
import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.domain.entity.Income;
import com.application.pennypal.domain.valueObject.IncomeStatus;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.Income.IncomeRepository;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.Income.PendingIncomeStatsProjection;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.category.CategoryRepository;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.IncomeEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper.CategoryMapper;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper.IncomeJpaMapper;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper.UserMapper;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.user.SpringDataUserRepository;
import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryUserResponseDTO;
import com.application.pennypal.interfaces.rest.mappers.CategoryDtoMapper;
import com.application.pennypal.shared.exception.ApplicationException;
import com.application.pennypal.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IncomeRepositoryAdapter implements IncomeRepositoryPort {
    private final IncomeRepository incomeRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final SpringDataUserRepository springDataUserRepository;
    private final UserMapper userMapper;
    private final IncomeJpaMapper incomeJpaMapper;
    @Override
    public Income save(Income income) {
        UserEntity user = springDataUserRepository.findById(income.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        CategoryEntity category = categoryRepository.findById(income.getCategoryId())
                .orElseThrow(() -> new ApplicationException("Category not found", "NOT_FOUND"));

        IncomeEntity incomeEntity = incomeJpaMapper.toEntity(income, user, category);
        IncomeEntity saved = incomeRepository.save(incomeEntity);

        return incomeJpaMapper.toDomain(saved);
    }

    @Override
    public Income update(Income income) {
        CategoryEntity category = categoryRepository.findById(income.getCategoryId())
                .orElseThrow(() -> new ApplicationException("Category not found","NOT_FOUND"));
        UserEntity user = springDataUserRepository.findById(income.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found exception"));
        IncomeEntity incomeEntity= new IncomeEntity(
                user,
                income.getTitle(),
                income.getAmount(),
                category,
                income.getIncomeDate(),
                income.getDescription(),
                income.getStatus(),
                income.isDeleted(),
                income.getIsRecurring(),
                income.getStartDate(),
                income.getEndDate(),
                income.getFrequency(),
                income.isRecurrenceActive(),
                income.getCreatedAt());
        // to update to same entry in the table
        incomeEntity.setId(income.getId());
        IncomeEntity updatedIncome = incomeRepository.save(incomeEntity);
        return incomeJpaMapper.toDomain(updatedIncome);
    }


    @Override
    public List<Income> getPagedIncomes(Long userId,int size,int page) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        List<IncomeEntity> incomeEntities =  incomeRepository.findAllByUserIdOrderByCreatedAtDesc(userId,pageable);
        return incomeEntities.stream()
                .map(incomeEntity -> new Income(incomeEntity.getId(),incomeEntity.getUser().getId(),incomeEntity.getTitle(),incomeEntity.getAmount(),incomeEntity.getCategory().getId(), incomeEntity.getIncomeDate(),
                        incomeEntity.getStatus(),incomeEntity.getDescription(),incomeEntity.getIsRecurring() ,incomeEntity.getStartDate(),incomeEntity.getEndDate(),incomeEntity.getFrequency(),incomeEntity.getIsRecurring(),incomeEntity.getCreatedAt(),incomeEntity.getUpdatedAt(),incomeEntity.getDeleted())).toList();
    }

    @Override
    public List<RecurringIncomeOutput> getRecurringIncomesData(Long userId) { /// need to rework
        List<IncomeEntity> incomeEntities = incomeRepository.findAllByUserIdAndIsRecurringTrueAndDeletedFalseOrderByIncomeDateDesc(userId);
        return incomeEntities.stream()
                .map(incomeEntity -> new RecurringIncomeOutput(incomeEntity.getId(),incomeEntity.isRecurrenceActive(),
                        incomeEntity.getAmount(),incomeEntity.getStartDate(),incomeEntity.getEndDate(),incomeEntity.getTitle(),incomeEntity.getIsRecurring(),
                        incomeEntity.getFrequency(), CategoryDtoMapper.toResponse(CategoryApplicationMapper.toOutput(categoryMapper.toDomain(incomeEntity.getCategory()))))).toList();
    }

    @Override
    public Optional<Income> getIncomeById(Long incomeId) {
        return incomeRepository.findById(incomeId).map(incomeJpaMapper::toDomain);
    }

    @Override
    public void deleteById(Long incomeId) {
        IncomeEntity income = incomeRepository.findById(incomeId)
                .orElseThrow(() -> new ApplicationException("Income not found","NOT_FOUND"));
        income.setDeleted(true);
        income.setRecurrenceActive(false);
        incomeRepository.save(income);
    }


    @Override
    public List<Income> findAllActiveRecurringIncomes() {
        return incomeRepository.findAllByIsRecurringTrueAndRecurrenceActiveTrueAndDeletedFalse().stream()
                .map(incomeJpaMapper::toDomain).toList();
    }

    @Override
    public PendingIncomeSummaryOutput getTotalPendingOneTimeIncome(Long userId, LocalDate startDate, LocalDate endDate) {
        PendingIncomeStatsProjection stats = incomeRepository.getPendingOneTimeIncomeStats(userId,startDate,endDate);
        return new PendingIncomeSummaryOutput(stats.getTotalAmount(),stats.getCount());
    }

    @Override
    public Integer countActiveRecurringIncomeByUserId(Long userId) {
        return incomeRepository.countActiveRecurringIncomeByUserId(userId);
    }

    @Override
    public boolean existsDuplicateIncome(Long userId, BigDecimal amount, String title, LocalDate incomeDate) {
        return incomeRepository.existsDuplicateIncome(userId,amount,title,incomeDate);
    }

    @Override
    public List<Income> findAllOneTimePendingIncomes(Long userId,LocalDate date) {
        return incomeRepository.findPendingNonRecurringIncomeBeforeDate(userId,date, IncomeStatus.PENDING).stream()
                .map(incomeJpaMapper::toDomain).toList();
    }


}
