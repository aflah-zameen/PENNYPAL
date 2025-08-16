package com.application.pennypal.infrastructure.persistence.jpa.entity;

import com.application.pennypal.domain.valueObject.GoalWithdrawRequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class GoalWithdrawRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,updatable = false,unique = true)
    private String withdrawId;

    @Column(nullable = false,unique = false)
    private String email;

    @Column(nullable = false,unique = true)
    private String goalId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private GoalWithdrawRequestStatus status;

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

    public GoalWithdrawRequestEntity(
            String withdrawId,
            String email,
            String goalId,
            BigDecimal amount,
            GoalWithdrawRequestStatus status
    ){
        this.withdrawId = withdrawId;
        this.email = email;
        this.goalId = goalId;
        this.amount = amount;
        this.status = status;
    }


}
