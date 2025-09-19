package com.application.pennypal.infrastructure.adapter.out.category;


import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.application.port.out.repository.CategoryManagementRepositoryPort;
import com.application.pennypal.domain.catgeory.entity.Category;
import com.application.pennypal.infrastructure.persistence.jpa.category.CategoryRepository;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.CategoryJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryManagementRepositoryPort {
    private final CategoryRepository categoryRepository;
    @Override
    public Category save(Category category) {

        CategoryEntity categoryEntity = categoryRepository.save(CategoryJpaMapper.toEntity(category));
        return CategoryJpaMapper.toDomain(categoryEntity);
    }

    @Override
    public Category update(Category category, String categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        categoryEntity.setName(category.getName());
        categoryEntity.setUsageTypes(new ArrayList<>(category.getUsageTypes()));
        categoryEntity.setActive(category.isActive());
        categoryEntity.setDefault(category.isDefault());
        categoryEntity.setColor(category.getColor());
        categoryEntity.setIcon(category.getIcon());
        categoryEntity.setDescription(category.getDescription());
        categoryEntity.setUsageCount(category.getUsageCount());

        CategoryEntity updatedEntity = categoryRepository.save(categoryEntity);
        return CategoryJpaMapper.toDomain(updatedEntity);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name)
                .map(CategoryJpaMapper::toDomain);
    }

    @Override
    public List<Category> findAll(){
        return categoryRepository.findAll().stream().map(CategoryJpaMapper::toDomain).toList();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id).map(CategoryJpaMapper::toDomain);
    }

    @Override
    public void deleteById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<CategoryUserOutput> getUserCategories() {
        List<CategoryEntity> categories = categoryRepository.findAllByIsActiveTrue();
        return categories.stream().map(categoryEntity -> new CategoryUserOutput(categoryEntity.getCategoryId(),categoryEntity.getName(),categoryEntity.getUsageTypes(),
                categoryEntity.isActive(),categoryEntity.isDefault(),categoryEntity.getSortOrder(),categoryEntity.getColor(),categoryEntity.getIcon())).toList();
    }

    @Override
    public Optional<Category> findByCategoryId(String categoryId) {
        return categoryRepository.findByCategoryId(categoryId)
                .map(CategoryJpaMapper::toDomain);
    }

    @Override
    public void deleteByCategoryId(String categoryId) {
        categoryRepository.deleteByCategoryId(categoryId);
    }
}
