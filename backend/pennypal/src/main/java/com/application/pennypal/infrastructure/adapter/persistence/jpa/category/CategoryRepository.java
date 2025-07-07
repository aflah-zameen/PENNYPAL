package com.application.pennypal.infrastructure.adapter.persistence.jpa.category;

import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long>{
    Optional<CategoryEntity> findByName(String name);
    List<CategoryEntity> findAllByIsActiveTrue();
}
