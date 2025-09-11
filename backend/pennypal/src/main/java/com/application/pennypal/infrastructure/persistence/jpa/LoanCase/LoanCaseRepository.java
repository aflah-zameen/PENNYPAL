package com.application.pennypal.infrastructure.persistence.jpa.LoanCase;

import com.application.pennypal.domain.LoanCase.LoanCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanCaseRepository extends JpaRepository<LoanCaseEntity,Long> {
    Optional<LoanCaseEntity> findByCaseId(String caseId);
}
