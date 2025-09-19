package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.domain.catgeory.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryManagementRepositoryPort {
    Category save (Category category );
    Category update (Category category, String categoryId );
    Optional<Category> findByName(String name);
    List<Category> findAll();
    Optional<Category> findById(Long id);
    List<CategoryUserOutput> getUserCategories();
    Optional<Category> findByCategoryId(String categoryId);
    void deleteByCategoryId(String categoryId);
}
