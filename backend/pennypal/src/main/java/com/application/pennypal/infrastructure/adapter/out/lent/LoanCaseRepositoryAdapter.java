package com.application.pennypal.infrastructure.adapter.out.lent;

import com.application.pennypal.application.dto.output.lend.LoanCaseOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.out.repository.LoanCaseRepositoryPort;
import com.application.pennypal.domain.LoanCase.LoanCase;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.LoanCase.LoanCaseEntity;
import com.application.pennypal.infrastructure.persistence.jpa.LoanCase.LoanCaseRepository;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.LoanCaseJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LoanCaseRepositoryAdapter implements LoanCaseRepositoryPort {
    private final LoanCaseRepository loanCaseRepository;
    @Override
    public LoanCase save(LoanCase loanCase) {
        LoanCaseEntity entity = LoanCaseJpaMapper.toEntity(loanCase);
        entity = loanCaseRepository.save(entity);
        return LoanCaseJpaMapper.toDomain(entity);
    }

    @Override
    public List<LoanCase> getAll() {
        return loanCaseRepository.findAll().stream()
                .map(LoanCaseJpaMapper::toDomain)
                .toList();
    }

    @Override
    public LoanCase getLoanCase(String caseId) {
        return loanCaseRepository.findByCaseId(caseId).map(LoanCaseJpaMapper::toDomain)
                .orElseThrow(() -> new InfrastructureException("Loan case cannot be found","NOT_FOUND"));
    }

    @Override
    public void update(LoanCase loanCase) {
        LoanCaseEntity entity = loanCaseRepository.findByCaseId(loanCase.getCaseId())
                .orElseThrow(() -> new InfrastructureException("Loan case not found","NOT_FOUND"));
        entity.setAdminNotes(loanCase.getAdminNotes());
        entity.setStatus(loanCase.getStatus());
        entity.setClosedAt(loanCase.getClosedAt());
        loanCaseRepository.save(entity);
    }
}
