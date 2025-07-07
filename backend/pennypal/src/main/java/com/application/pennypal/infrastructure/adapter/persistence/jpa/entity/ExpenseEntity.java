package com.application.pennypal.infrastructure.adapter.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expense")
@NoArgsConstructor
@Setter
@Getter
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name",nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "amount",nullable = false)
    private BigDecimal amount;

    @Column(name = "type",nullable = false)
    private String type;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private final Boolean active = true;

    private final LocalDateTime createdAt = LocalDateTime.now();

    public ExpenseEntity(Long userId,String name,CategoryEntity category,BigDecimal amount,
                         String type,LocalDate startDate,LocalDate endDate){
        this.userId = userId;
        this.name=name;
        this.category = category;
        this.amount = amount;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
