package com.application.pennypal.infrastructure.adapter.persistence.jpa.entity;

import com.application.pennypal.domain.user.valueObject.RecurrenceFrequency;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity source;

    @Column(name = "income_date",nullable = false)
    private LocalDate incomeDate;

    @Column(nullable = false)
    private Boolean recurrence;

    @Enumerated(EnumType.STRING)
    private RecurrenceFrequency frequency;

    private boolean recurrenceActive = true;

    private String notes;

    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();

    private String status;

    public IncomeEntity(Long userId,BigDecimal amount,CategoryEntity source,LocalDate incomeDate,String notes,
                        Boolean recurrence, RecurrenceFrequency frequency,boolean recurrenceActive){
        this.userId = userId;
        this.amount= amount;
        this.source = source;
        this.incomeDate = incomeDate;
        this.notes = notes;
        this.recurrence = recurrence;
        this.frequency = frequency;
        this.recurrenceActive = recurrenceActive;
    }
}
