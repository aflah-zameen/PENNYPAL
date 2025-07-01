package com.application.pennypal.infrastructure.adapter.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "income")
@NoArgsConstructor
@Getter
@Setter
public class IncomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private BigDecimal amount;

    private String source;
    @Column(name = "income_date",nullable = false)
    private LocalDate incomeDate;

    private String notes;

    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();

    private String status;

    public IncomeEntity(Long userId,BigDecimal amount,String source,LocalDate incomeDate,String notes){
        this.userId = userId;
        this.amount= amount;
        this.source = source;
        this.incomeDate = incomeDate;
        this.notes = notes;
    }
}
