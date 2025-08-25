package com.application.pennypal.infrastructure.adapter.out.lend;

import com.application.pennypal.application.port.out.repository.LoanRepositoryPort;
import com.application.pennypal.domain.lend.LendingRequest;
import com.application.pennypal.domain.lend.Loan;
import com.application.pennypal.domain.lend.LoanStatus;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.lend.LendingRequestEntity;
import com.application.pennypal.infrastructure.persistence.jpa.lend.LendingRequestRepository;
import com.application.pennypal.infrastructure.persistence.jpa.lend.LoanEntity;
import com.application.pennypal.infrastructure.persistence.jpa.lend.LoanRepository;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.LoanJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.application.pennypal.domain.lend.LoanStatus.*;

@Component
@RequiredArgsConstructor
public class LoanRepositoryAdapter implements LoanRepositoryPort {
    private final LoanRepository loanRepository;
    private final LendingRequestRepository lendingRequestRepository;
    @Override
    public Loan save(Loan loan) {
        LendingRequestEntity requestEntity = lendingRequestRepository.findByRequestId(loan.getRequestId())
                .orElseThrow(() -> new InfrastructureException("Lending Request Entity not found","NOT_FOUND"));
        LoanEntity entity = LoanJpaMapper.toEntity(loan,requestEntity);
        entity = loanRepository.save(entity);
        return LoanJpaMapper.toDomain(entity);
    }

    @Override
    public Loan update(Loan loan) {
        LoanEntity loanEntity = loanRepository.findByLoanId(loan.getLoanId())
                .orElseThrow(() -> new InfrastructureException("Loan Entity not found","NOT_FOUND"));
        loanEntity.setAmountPaid(loan.getAmountPaid());
        loanEntity.setStatus(loan.getStatus());
        loanEntity = loanRepository.save(loanEntity);
        return LoanJpaMapper.toDomain(loanEntity);
    }

    @Override
    public Optional<Loan> getLoan(String loanId) {
        return loanRepository.findByLoanId(loanId).map(LoanJpaMapper::toDomain);

    }

    @Override
    public BigDecimal getTotalAmountLent(String userId) {
        return loanRepository.sumTotalAmountLent(userId);
    }

    @Override
    public BigDecimal getTotalAmountBorrowed(String userId) {
        return loanRepository.sumTotalAmountBorrowed(userId);
    }

    @Override
    public long getActiveLoanSentCount(String userId) {
        return loanRepository.countByLendingRequest_RequestedToAndStatusIn(userId, List.of(ACTIVE,PARTIAL,OVERDUE));
    }

    @Override
    public long getActiveLoanReceived(String userId) {
        return loanRepository.countByLendingRequest_RequestedByAndStatusIn(userId,List.of(ACTIVE,PARTIAL,OVERDUE));
    }

    @Override
    public long getOverdueLoanCount(String userId) {
        return loanRepository.countByLendingRequest_RequestedByAndDeadlineBeforeAndStatusIn(userId, LocalDateTime.now(),List.of(ACTIVE,PARTIAL));
    }

    @Override
    public List<Loan> getLoansToRepay(String userId) {
        return loanRepository.findAllByLendingRequest_RequestedByAndStatusIn(userId,List.of(ACTIVE,PARTIAL,OVERDUE)).stream()
                .map(LoanJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<Loan> getLoansToCollect(String userId) {
        return loanRepository.findAllByLendingRequest_RequestedToAndStatusIn(userId,List.of(ACTIVE,PARTIAL,OVERDUE)).stream()
                .map(LoanJpaMapper::toDomain)
                .toList();
    }
}
