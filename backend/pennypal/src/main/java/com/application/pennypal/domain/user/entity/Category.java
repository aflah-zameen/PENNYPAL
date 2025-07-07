package com.application.pennypal.domain.user.entity;
import com.application.pennypal.domain.user.valueObject.CategoryType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
public class Category {
    private  Long id;
    private  Long createdBy;
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


}
