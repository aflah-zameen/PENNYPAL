package com.application.pennypal.infrastructure.adapter.income;

import com.application.pennypal.application.dto.RecurringIncomeDTO;
import com.application.pennypal.application.dto.RecurringIncomesDataDTO;
import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.domain.user.entity.Income;
import com.application.pennypal.domain.user.valueObject.IncomeDTO;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.Income.IncomeRepository;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.category.CategoryRepository;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.IncomeEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper.CategoryMapper;
import com.application.pennypal.shared.exception.ApplicationException;
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
    @Override
    public Income save(IncomeDTO income, Long userId) {
        CategoryEntity category = categoryRepository.findById(income.source())
                .orElseThrow(() -> new ApplicationException("Category not found","NOT_FOUND"));
        IncomeEntity incomeEntity = new IncomeEntity(
                userId,income.amount(),category,income.income_date(),income.notes(),income.recurrence(),income.frequency(),income.recurrenceActive()
        );
        incomeEntity.setStatus(incomeEntity.getIncomeDate().isAfter(LocalDate.now())? "PENDING":"CONFIRMED" );
        IncomeEntity entity = incomeRepository.save(incomeEntity);
        return new Income(entity.getId(),entity.getUserId(),entity.getAmount(),categoryMapper.toDomain(entity.getSource()),entity.getIncomeDate(),
                entity.getStatus(),entity.getCreatedAt(),incomeEntity.getNotes(),incomeEntity.getRecurrence(),incomeEntity.getFrequency(),incomeEntity.isRecurrenceActive());
    }

    @Override
    public Income update(Income income) {
        IncomeEntity incomeEntity= new IncomeEntity();
        incomeEntity.setId(income.getId());
        incomeEntity.setAmount(income.getAmount());
        incomeEntity.setIncomeDate(income.getIncome_date());
        incomeEntity.setNotes(income.getNotes());
        incomeEntity.setFrequency(income.getFrequency());
        incomeEntity.setSource(categoryMapper.toEntity(income.getSource()));
        incomeEntity.setStatus(income.getStatus());
        incomeEntity.setRecurrence(income.getRecurrence());
        incomeEntity.setRecurrenceActive(income.isRecurrenceActive());
        incomeEntity.setUserId(income.getUserId());
        incomeEntity = incomeRepository.save(incomeEntity);
        return new Income(incomeEntity.getId(),incomeEntity.getUserId(),incomeEntity.getAmount(),categoryMapper.toDomain(incomeEntity.getSource()),incomeEntity.getIncomeDate(),incomeEntity.getStatus(),incomeEntity.getCreatedAt(),
                incomeEntity.getNotes(),incomeEntity.getRecurrence(),incomeEntity.getFrequency(),incomeEntity.isRecurrenceActive());
    }

    @Override
    public BigDecimal getTotalIncomeByUserIdAndDate(Long userId, LocalDate date) {
        return incomeRepository.getTotalIncomeByUserIdAndDate(userId,date);
    }

    @Override
    public List<Income> getPagedIncomes(Long userId,int size,int page) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        List<IncomeEntity> incomeEntities =  incomeRepository.findAllByUserIdOrderByCreatedAtDesc(userId,pageable);
        return incomeEntities.stream()
                .map(incomeEntity -> {
                    return new Income(incomeEntity.getId(),incomeEntity.getUserId(),incomeEntity.getAmount(), categoryMapper.toDomain(incomeEntity.getSource()), incomeEntity.getIncomeDate(),
                            incomeEntity.getStatus(), incomeEntity.getCreatedAt(),incomeEntity.getNotes(),incomeEntity.getRecurrence(),incomeEntity.getFrequency(),incomeEntity.isRecurrenceActive());
                }).toList();
    }

    @Override
    public List<RecurringIncomeDTO> getRecurringIncomesData(Long userId) {
        List<IncomeEntity> incomeEntities = incomeRepository.findAllByUserIdAndRecurrenceTrueOrderByIncomeDateDesc(userId);
        return incomeEntities.stream()
                .map(incomeEntity -> new RecurringIncomeDTO(incomeEntity.getId(),incomeEntity.isRecurrenceActive(),
                        incomeEntity.getAmount(),incomeEntity.getIncomeDate(),incomeEntity.getSource().getName(),incomeEntity.getRecurrence(),
                        incomeEntity.getFrequency())).toList();
    }

    @Override
    public Optional<Income> getIncomeById(Long incomeId) {
        return incomeRepository.findById(incomeId).map(incomeEntity -> new Income(incomeEntity.getId(),
                incomeEntity.getUserId(),incomeEntity.getAmount(),categoryMapper.toDomain(incomeEntity.getSource()),incomeEntity.getIncomeDate(),
                incomeEntity.getStatus(),incomeEntity.getCreatedAt(),incomeEntity.getNotes(),incomeEntity.getRecurrence(),
                incomeEntity.getFrequency(),incomeEntity.isRecurrenceActive()));
    }

    @Override
    public void deleteById(Long incomeId) {
        this.incomeRepository.deleteById(incomeId);
    }


}
