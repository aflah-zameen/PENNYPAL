package com.application.pennypal.application.port;

import com.application.pennypal.application.dto.CategoryUserResponseDTO;
import com.application.pennypal.domain.user.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryManagementRepositoryPort {
    Category save (Category category,Long userId );
    Optional<Category> findByName(String name);
    List<Category> findAll();
    Optional<Category> findById(Long id);
    void deleteById(Long categoryId);
    List<CategoryUserResponseDTO> getUserCategories();
}
