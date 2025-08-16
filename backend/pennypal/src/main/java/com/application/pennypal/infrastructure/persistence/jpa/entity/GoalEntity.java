package com.application.pennypal.infrastructure.persistence.jpa.entity;

import com.application.pennypal.domain.valueObject.GoalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "goals")
@Getter
@Setter
public class GoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String goalId;
    // User who created this goal
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(name = "target_amount", nullable = false, precision = 14, scale = 2)
    private BigDecimal targetAmount;

    @Column(name = "current_amount", precision = 14, scale = 2)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoalStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(name = "priority_level")
    private Integer priorityLevel;

    @Column(nullable = false)
    private boolean deleted ;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public GoalEntity() {}

    public GoalEntity(String goalId,UserEntity user, String title,String description,
                      BigDecimal targetAmount,BigDecimal currentAmount,
                      LocalDate startDate,LocalDate endDate,GoalStatus status,CategoryEntity category, int priorityLevel,boolean deleted
                      ) {
        this.goalId =goalId;
        this.user = user;
        this.title = title;
        this.description = description;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.startDate = startDate;
        this.endDate= endDate;
        this.status = status;
        this.category = category;
        this.priorityLevel = priorityLevel;
        this.deleted =deleted;
    }

    // Lifecycle hooks
    @PrePersist
    protected void onCreate() {
        this.deleted = false;
        this.status = GoalStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
