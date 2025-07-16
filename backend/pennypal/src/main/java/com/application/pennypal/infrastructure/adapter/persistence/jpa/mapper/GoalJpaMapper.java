package com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper;

import com.application.pennypal.domain.entity.Goal;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.GoalEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface GoalJpaMapper {
    @Mappings({
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "categoryId",source = "category.id"),
            @Mapping(target = "status",source = "status")

    })
    Goal toDomain(GoalEntity goalEntity);

    @Mappings({
            @Mapping(target = "id",source = "goal.id"),
            @Mapping(target = "description", source = "goal.description"),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "createdAt", source = "goal.createdAt"),
            @Mapping(target = "user", source = "user"),
            @Mapping(target = "category", source = "category"),
            @Mapping(target = "status", source = "goal.status")
    })
    GoalEntity toEntity(Goal goal, UserEntity user, CategoryEntity category);

}
