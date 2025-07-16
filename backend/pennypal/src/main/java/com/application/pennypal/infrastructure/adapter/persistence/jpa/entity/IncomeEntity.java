package com.application.pennypal.infrastructure.adapter.persistence.jpa.entity;

import com.application.pennypal.domain.valueObject.IncomeStatus;
import com.application.pennypal.domain.valueObject.RecurrenceFrequency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "income")
@NoArgsConstructor
@Getter
@Setter
public class IncomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "income_date")
    private LocalDate incomeDate;

    private String description;

    @Enumerated(EnumType.STRING)
    private IncomeStatus status;

    private Boolean deleted;


    //recurrence related fields
    @Column(nullable = false)
    private Boolean isRecurring;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private RecurrenceFrequency frequency;

    private boolean recurrenceActive = true;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public IncomeEntity(UserEntity user,String title, BigDecimal amount, CategoryEntity category,
                        LocalDate incomeDate, String description,IncomeStatus status,Boolean deleted,
                        Boolean isRecurring,LocalDate startDate,LocalDate endDate, RecurrenceFrequency frequency, boolean recurrenceActive,LocalDateTime createdAt){
        this.user = user;
        this.title = title;
        this.amount= amount;
        this.category = category;
        this.incomeDate = incomeDate;
        this.description = description;
        this.status = status;
        this.deleted = deleted;

        this.isRecurring = isRecurring;
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
        this.recurrenceActive = recurrenceActive;
        this.createdAt = createdAt;

    }

    //Relations Defined
    @OneToMany(mappedBy = "income",cascade = CascadeType.ALL)
    private List<RecurringIncomeLogEntity> recurringIncomeLogs;



    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

}
