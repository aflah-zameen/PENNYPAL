package com.application.pennypal.infrastructure.persistence.jpa.category;

import com.application.pennypal.infrastructure.persistence.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long>{
    Optional<CategoryEntity> findByName(String name);
    List<CategoryEntity> findAllByIsActiveTrue();
    Optional<CategoryEntity> findByCategoryId(String categoryId);

    void deleteByCategoryId(String categoryId);

    @Query("SELECT c FROM CategoryEntity c WHERE c.deleted = false")
    List<CategoryEntity> findAllNonDeleted();
}
