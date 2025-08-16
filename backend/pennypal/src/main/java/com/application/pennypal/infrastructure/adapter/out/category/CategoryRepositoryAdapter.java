package com.application.pennypal.infrastructure.adapter.out.category;


import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.application.port.out.repository.CategoryManagementRepositoryPort;
import com.application.pennypal.domain.catgeory.entity.Category;
import com.application.pennypal.infrastructure.persistence.jpa.category.CategoryRepository;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryManagementRepositoryPort {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public Category save(Category category, String userId) {
        category.setCreatedBy(userId);
        CategoryEntity categoryEntity = categoryRepository.save(categoryMapper.toEntity(category));
        return categoryMapper.toDomain(categoryEntity);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryMapper::toDomain);
    }

    @Override
    public List<Category> findAll(){
        return categoryRepository.findAll().stream().map(categoryMapper::toDomain).toList();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id).map(categoryMapper::toDomain);
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
                .map(categoryMapper::toDomain);
    }

    @Override
    public void deleteByCategoryId(String categoryId) {
        categoryRepository.deleteByCategoryId(categoryId);
    }
}
