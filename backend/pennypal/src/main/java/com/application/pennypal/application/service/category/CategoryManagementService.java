package com.application.pennypal.application.service.category;

import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.out.repository.CategoryManagementRepositoryPort;
import com.application.pennypal.application.port.in.category.*;
import com.application.pennypal.application.port.in.expense.GetUserCategories;
import com.application.pennypal.domain.catgeory.entity.Category;
import com.application.pennypal.domain.valueObject.CategoryType;
import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryRequestDTO;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class CategoryManagementService implements CreateCategory,GetCategories, UpdateCategory,
        ToggleCategoryStatus, DeleteCategory, GetUserCategories, GetCategoryById {
    private final CategoryManagementRepositoryPort categoryManagementRepositoryPort;
    @Override
    public Category execute(CategoryRequestDTO category, String userId) {
        if(categoryManagementRepositoryPort.findByName(category.name()).isPresent()){
            throw new ApplicationBusinessException("Category already exist.","DUPLICATE_EXIST");
        }

        Category newCategory = Category.create(
                userId,
                category.name(),
                category.usageTypes().stream().map(CategoryType::valueOf).toList(),
                category.sortOrder(),
                category.description(),
                category.color(),
                category.isDefault(),
                category.icon());



        return categoryManagementRepositoryPort.save(newCategory);
    }

    @Override
    public List<Category> execute() {
        return categoryManagementRepositoryPort.findAll();
    }

    @Override
    public Category update(CategoryRequestDTO category, String categoryId, String userId){
        Category oldCategory = categoryManagementRepositoryPort.findByCategoryId(categoryId)
                .orElseThrow(() -> new UserNotFoundApplicationException("Category not found"));
        oldCategory.setName(category.name());
        oldCategory.setColor(category.color());
        oldCategory.setIcon(category.icon());
        oldCategory.setActive(category.active());
        oldCategory.setUpdatedAt(LocalDateTime.now());
        oldCategory.setDescription(category.description());
        oldCategory.setUsageTypes(category.usageTypes().stream().map(CategoryType::valueOf).toList());
        oldCategory.setDefault(category.isDefault());
        oldCategory.setSortOrder(category.sortOrder());
        oldCategory.setUsageCount(category.usageCount());
        return categoryManagementRepositoryPort.update(oldCategory,categoryId);
    }

    @Override
    public Category toggle(String categoryId,String userId) {
        Category category =  categoryManagementRepositoryPort.findByCategoryId(categoryId)
                .orElseThrow(() -> new ApplicationBusinessException("Category cannot be found.","NOT_FOUND"));
        category.setActive(!category.isActive());
        category.setUpdatedAt(LocalDateTime.now());
        return categoryManagementRepositoryPort.update(category,categoryId);
    }

    @Override
    public void delete(String categoryId) {
        this.categoryManagementRepositoryPort.deleteByCategoryId(categoryId);
    }

    @Override
    public List<CategoryUserOutput> get() {
        return this.categoryManagementRepositoryPort.getUserCategories();
    }

    @Override
    public CategoryUserOutput get(String categoryId) {
        Category category = categoryManagementRepositoryPort.findByCategoryId(categoryId)
                .orElseThrow(() -> new ApplicationBusinessException("Category not found","NOT_FOUND"));
        return new CategoryUserOutput(category.getCategoryId(),category.getName(),category.getUsageTypes(),
                category.isActive(),category.isDefault(),category.getSortOrder(),category.getColor(),category.getIcon());
    }
}
