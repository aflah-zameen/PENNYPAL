package com.application.pennypal.infrastructure.adapter.income;

import com.application.pennypal.application.port.IncomeRepositoryPort;
import com.application.pennypal.domain.user.valueObject.IncomeDTO;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.Income.IncomeRepository;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.IncomeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class IncomeRepositoryAdapter implements IncomeRepositoryPort {
    private final IncomeRepository incomeRepository;
    @Override
    public IncomeDTO save(IncomeDTO income,Long userId) {
        IncomeEntity incomeEntity = new IncomeEntity(
                userId,income.amount(),income.source(),income.income_date(),income.notes()
        );
        incomeEntity.setStatus(incomeEntity.getIncomeDate().isAfter(LocalDate.now())? "PENDING":"CONFIRMED" );
        IncomeEntity entity = incomeRepository.save(incomeEntity);
        return new IncomeDTO(entity.getAmount(),entity.getSource(),entity.getIncomeDate(),
                entity.getStatus(),entity.getCreatedAt(),entity.getNotes());
    }

    @Override
    public BigDecimal getTotalIncomeByUserIdAndDate(Long userId, LocalDate date) {
        return incomeRepository.getTotalIncomeByUserIdAndDate(userId,date);
    }

    @Override
    public List<IncomeDTO> getAllIncomes(Long userId) {
        List<IncomeEntity> incomeEntities =  incomeRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
        return incomeEntities.stream()
                .map(incomeEntity -> {
                    return new IncomeDTO(incomeEntity.getAmount(), incomeEntity.getSource(), incomeEntity.getIncomeDate(),
                            incomeEntity.getStatus(), incomeEntity.getCreatedAt(),incomeEntity.getNotes());
                }).toList();
    }


}
