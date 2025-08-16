package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.port.out.repository.CategoryManagementRepositoryPort;
import com.application.pennypal.application.service.category.CategoryManagementService;
import com.application.pennypal.application.port.in.category.CreateCategory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryBeanConfig {
    @Bean
    CreateCategory createCategory(CategoryManagementRepositoryPort categoryManagementRepositoryPort){
        return new CategoryManagementService(categoryManagementRepositoryPort);
    }
}
