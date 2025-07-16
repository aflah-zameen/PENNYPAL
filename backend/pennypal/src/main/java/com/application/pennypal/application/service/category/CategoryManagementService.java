package com.application.pennypal.application.service.category;

import com.application.pennypal.application.output.category.CategoryUserOutput;
import com.application.pennypal.application.port.CategoryManagementRepositoryPort;
import com.application.pennypal.application.usecases.category.*;
import com.application.pennypal.application.usecases.expense.GetUserCategories;
import com.application.pennypal.domain.entity.Category;
import com.application.pennypal.shared.exception.ApplicationException;
import com.application.pennypal.shared.exception.DuplicateException;
import com.application.pennypal.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class CategoryManagementService implements CreateCategory,GetCategories,UpdateCategory,
        ToggleCategoryStatus, DeleteCategory, GetUserCategories,GetCategoryById {
    private final CategoryManagementRepositoryPort categoryManagementRepositoryPort;
    @Override
    public Category execute(Category category, Long userId) {
        if(categoryManagementRepositoryPort.findByName(category.getName()).isPresent()){
            throw new DuplicateException("Category already exist.");
        }
        return categoryManagementRepositoryPort.save(category,userId);
    }

    @Override
    public List<Category> execute() {
        return categoryManagementRepositoryPort.findAll();
    }

    @Override
    public Category update(Category category,Long categoryId,Long userId){
        Category oldCategory = categoryManagementRepositoryPort.findById(categoryId)
                .orElseThrow(() -> new UserNotFoundException("Category not found"));
        oldCategory.setName(category.getName());
        oldCategory.setColor(category.getColor());
        oldCategory.setIcon(category.getIcon());
        oldCategory.setActive(category.isActive());
        oldCategory.setUpdatedAt(LocalDateTime.now());
        oldCategory.setDescription(category.getDescription());
        oldCategory.setUsageTypes(category.getUsageTypes());
        oldCategory.setDefault(category.isDefault());
        oldCategory.setSortOrder(category.getSortOrder());
        oldCategory.setCreatedAt(category.getCreatedAt());
        oldCategory.setUsageCount(category.getUsageCount());
        return categoryManagementRepositoryPort.save(oldCategory,userId);
    }

    @Override
    public Category toggle(Long categoryId,Long userId) {
        Category category =  categoryManagementRepositoryPort.findById(categoryId)
                .orElseThrow(() -> new ApplicationException("Category cannot be found.","NOT_FOUND"));
        category.setActive(!category.isActive());
        category.setUpdatedAt(LocalDateTime.now());
        return categoryManagementRepositoryPort.save(category,userId);
    }

    @Override
    public void delete(Long categoryId) {
        this.categoryManagementRepositoryPort.deleteById(categoryId);
    }

    @Override
    public List<CategoryUserOutput> get() {
        return this.categoryManagementRepositoryPort.getUserCategories();
    }

    @Override
    public CategoryUserOutput get(Long categoryId) {
        Category category = categoryManagementRepositoryPort.findById(categoryId)
                .orElseThrow(() -> new ApplicationException("Category not found","NOT_FOUND"));
        return new CategoryUserOutput(category.getId(),category.getName(),category.getUsageTypes(),
                category.isActive(),category.isDefault(),category.getSortOrder(),category.getColor(),category.getIcon());
    }
}
