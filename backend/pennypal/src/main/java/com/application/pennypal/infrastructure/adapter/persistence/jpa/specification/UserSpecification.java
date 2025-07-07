package com.application.pennypal.infrastructure.adapter.persistence.jpa.specification;

import com.application.pennypal.domain.user.valueObject.Roles;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Component
public class UserSpecification {
    public static Specification<UserEntity> hasRole(String role){
        return ((root, query, criteriaBuilder) ->
                role == null ?
                        null : criteriaBuilder.isMember(role,root.get("roles")));
    }

    public static Specification<UserEntity> hasStatus(Boolean status){
        return ((root, query, criteriaBuilder) ->
                status == null ?
                        null : criteriaBuilder.equal(root.get("active"),status));
    }

    public static Specification<UserEntity> joinedAfter(LocalDateTime joinedAfter){
        return ((root, query, criteriaBuilder) ->
                joinedAfter == null ?
                        null : criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"),joinedAfter));
    }

    public static Specification<UserEntity> joinedBefore(LocalDateTime joinedBefore){
        return ((root, query, criteriaBuilder) ->
                joinedBefore == null ?
                null : criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"),joinedBefore));
    }

    public static Specification<UserEntity> keywordLike(String keyword){
        return ((root, query, criteriaBuilder) ->{
            if(keyword == null || keyword.isBlank()) return null;
            String pattern = "%"+keyword.toLowerCase()+"%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),pattern)
            );
        });
    }
}
