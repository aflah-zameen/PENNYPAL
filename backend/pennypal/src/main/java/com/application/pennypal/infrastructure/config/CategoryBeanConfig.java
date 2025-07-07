package com.application.pennypal.infrastructure.config;

import com.application.pennypal.application.port.CategoryManagementRepositoryPort;
import com.application.pennypal.application.service.category.CategoryManagementService;
import com.application.pennypal.application.usecases.category.CreateCategory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryBeanConfig {
    @Bean
    CreateCategory createCategory(CategoryManagementRepositoryPort categoryManagementRepositoryPort){
        return new CategoryManagementService(categoryManagementRepositoryPort);
    }
}
