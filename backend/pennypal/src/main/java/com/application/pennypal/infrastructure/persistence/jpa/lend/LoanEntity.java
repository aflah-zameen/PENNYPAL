package com.application.pennypal.infrastructure.persistence.jpa.lend;
import com.application.pennypal.domain.lend.LoanStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String loanId;

    @OneToOne
    @JoinColumn(name = "request_id", referencedColumnName = "requestId")
    private LendingRequestEntity lendingRequest;

    @Column(nullable = false)
    private BigDecimal amount;

    @Setter
    @Column(nullable = false)
    private BigDecimal amountPaid;

    @Column(nullable = false)
    private LocalDateTime acceptedAt;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    @Setter
    private LocalDateTime lastReminderSentAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /// constructor
    public LoanEntity(
       String loanId,
       LendingRequestEntity lendingRequest,
       BigDecimal amount,
       BigDecimal amountPaid,
       LocalDateTime acceptedAt,
       LocalDateTime deadline,
       LoanStatus status,
       LocalDateTime lastReminderSentAt
    ){
        this.loanId = loanId;
        this.lendingRequest = lendingRequest;
        this.amount = amount;
        this.amountPaid = amountPaid;
        this.acceptedAt = acceptedAt;
        this.deadline = deadline;
        this.status = status;
        this.lastReminderSentAt = lastReminderSentAt;
    }

}
