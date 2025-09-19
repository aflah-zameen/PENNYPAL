package com.application.pennypal.infrastructure.persistence.jpa.entity;

import com.application.pennypal.domain.valueObject.CategoryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,updatable = false,unique = true)
    private String categoryId;
    private String name;
    private String createdBy;
    @ElementCollection(targetClass = CategoryType.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "category_usage_types", joinColumns = @JoinColumn(name = "category_id"))
    @Column(name = "usage_type")
    private List<CategoryType> usageTypes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int sortOrder;
    private String description;
    private String color;
    private boolean isActive;
    private boolean isDefault;
    private String icon;
    private int usageCount;

    @OneToMany(mappedBy = "category" ,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TransactionEntity> transactionEntities = new ArrayList<>();
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<GoalEntity> goals = new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
