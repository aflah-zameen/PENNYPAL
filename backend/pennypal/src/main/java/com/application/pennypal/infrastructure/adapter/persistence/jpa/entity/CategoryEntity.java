package com.application.pennypal.infrastructure.adapter.persistence.jpa.entity;

import com.application.pennypal.domain.user.entity.Category;
import com.application.pennypal.domain.user.valueObject.CategoryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long createdBy;
    @Enumerated(EnumType.STRING)
    private List<CategoryType> usageTypes;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private int sortOrder;
    private String description;
    private String color;
    private boolean isActive;
    private boolean isDefault;
    private String icon;
    private int usageCount;

    @OneToMany(mappedBy = "source" ,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<IncomeEntity> incomeEntities = new ArrayList<>();
    @OneToMany(mappedBy = "category" ,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ExpenseEntity> expenseEntities = new ArrayList<>();

}
