package com.application.pennypal.domain.catgeory.entity;
import com.application.pennypal.domain.valueObject.CategoryType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {
    private  String categoryId;
    private  String createdBy;
    private String name;
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


    //Business methods
    public void activate() {
        this.isActive = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.isActive = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void rename(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = newName;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateUsageCount(int delta) {
        this.usageCount += delta;
        if (this.usageCount < 0) this.usageCount = 0;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeColor(String newColor) {
        if (newColor != null && !newColor.trim().isEmpty()) {
            this.color = newColor;
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void updateDescription(String newDescription) {
        this.description = newDescription;
        this.updatedAt = LocalDateTime.now();
    }

    public static Category create(
            String createdBy,
            String name,
            List<CategoryType> categoryTypes,
            int sortOrder,
            String description,
            String color,
            boolean isDefault,
            String icon
    ){
        String categoryId = "CATE_"+ UUID.randomUUID();
        return new Category(
                categoryId,
                createdBy,
                name,
                categoryTypes,
                null,
                null,
                sortOrder,
                description,
                color,
                true,
                isDefault,
                icon,
                0
        );
    }

    public static Category reconstruct(String categoryId,
                                String createdBy,
                                String name,
                                List<CategoryType> categoryTypes,
                                LocalDateTime createdAt,
                                LocalDateTime updatedAt,
                                int sortOrder,
                                String description,
                                String color,
                                boolean isActive,
                                boolean isDefault,
                                String icon,
                                int usageCount){
        return new Category(
                categoryId,
                createdBy,
                name,
                categoryTypes,
                createdAt,
                updatedAt,
                sortOrder,
                description,
                color,
                isActive,
                isDefault,
                icon,
                usageCount
        );
    }


}
