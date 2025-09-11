package com.application.pennypal.infrastructure.persistence.jpa.LoanCase;

import com.application.pennypal.domain.LoanCase.LoanCaseStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@NoArgsConstructor
public class LoanCaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false,updatable = false)
    private String caseId;
    @Column(nullable = false)
    private String loanId;
    @Column(nullable = false)
    private String filedBy;
    @Column(nullable = false)
    private String reason;
    @Column(nullable = false)
    private LocalDateTime filedAt;
    @Setter
    @Column(nullable = false)
    private LoanCaseStatus status;
    @Setter
    private String adminNotes;
    @Setter
    private LocalDateTime closedAt;
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public static LoanCaseEntity create(String caseId,
                          String loanId,
                          String filedBy,
                          String reason,
                          LocalDateTime filedAt,
                          LoanCaseStatus status){
        return new LoanCaseEntity(
                caseId,
                loanId,
                filedBy,
                reason,
                filedAt,
                status,
                null,
                null
        ) ;
    }

    public LoanCaseEntity(String caseId,
                          String loanId,
                          String filedBy,
                          String reason,
                          LocalDateTime filedAt,
                          LoanCaseStatus status,
                          String adminNotes,
                          LocalDateTime closedAt){
        this.caseId = caseId;
        this.loanId = loanId;
        this.filedBy = filedBy;
        this.reason = reason;
        this.filedAt = filedAt;
        this.status = status;
        this.adminNotes = adminNotes;
        this.closedAt = closedAt;
    }
}
