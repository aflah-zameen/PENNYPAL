package com.application.pennypal.application.port;

import com.application.pennypal.application.output.category.CategoryUserOutput;
import com.application.pennypal.domain.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryManagementRepositoryPort {
    Category save (Category category,Long userId );
    Optional<Category> findByName(String name);
    List<Category> findAll();
    Optional<Category> findById(Long id);
    void deleteById(Long categoryId);
    List<CategoryUserOutput> getUserCategories();
}
