package com.application.pennypal.infrastructure.persistence.jpa.lend;

import com.application.pennypal.domain.lend.LendingRequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class LendingRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,updatable = false,unique = true)
    private String requestId;

    @Column(nullable = false)
    private String requestedBy;

    @Column(nullable = false)
    private String requestedTo;

    @Column(nullable = false)
    private BigDecimal amount;

    private String message;

    @Column(nullable = false)
    private LocalDateTime proposedDeadline;

    private LocalDateTime acceptedDeadline;

    @Setter
    @Column(nullable = false)
    private LendingRequestStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public LendingRequestEntity(
            String requestId,
            String requestedBy,
            String requestedTo,
            BigDecimal amount,
            String message,
            LocalDateTime proposedDeadline,
            LocalDateTime acceptedDeadline,
            LendingRequestStatus status
    ){
        this.requestId = requestId;
        this.requestedBy = requestedBy;
        this.requestedTo = requestedTo;
        this.amount = amount;
        this.message = message;
        this.proposedDeadline = proposedDeadline;
        this.acceptedDeadline = acceptedDeadline;
        this.status = status;
    }

}
