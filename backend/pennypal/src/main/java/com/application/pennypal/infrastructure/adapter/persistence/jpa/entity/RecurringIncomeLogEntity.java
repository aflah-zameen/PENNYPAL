package com.application.pennypal.infrastructure.adapter.persistence.jpa.entity;

import com.application.pennypal.domain.valueObject.RecurringIncomeLogStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "recurring_income_logs")
@NoArgsConstructor
public class RecurringIncomeLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private RecurringIncomeLogStatus status;


    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "income_id")
    private IncomeEntity income;

    public RecurringIncomeLogEntity(UserEntity user, BigDecimal amount,
                                    LocalDate date, RecurringIncomeLogStatus status, IncomeEntity income){
        this.user = user;
        this.amount = amount;
        this.date =date;
        this.status = status;
        this.income = income;
    }


    @PrePersist
    protected void onCreate(){
        this.deleted = false;
    }


}
