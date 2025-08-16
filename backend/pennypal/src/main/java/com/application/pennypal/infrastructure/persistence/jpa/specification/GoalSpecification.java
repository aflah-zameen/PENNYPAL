package com.application.pennypal.infrastructure.persistence.jpa.specification;

import com.application.pennypal.application.dto.input.goal.GoalAdminFilter;
import com.application.pennypal.domain.valueObject.GoalStatus;
import com.application.pennypal.infrastructure.persistence.jpa.entity.GoalEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class GoalSpecification {

    public static Specification<GoalEntity> hasUserId(String userId) {
        return (root, query, cb) -> {
            if (userId == null || userId.isBlank()) return null;
            return cb.equal(root.get("user").get("userId"), userId);
        };
    }

    public static Specification<GoalEntity> keywordLike(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) return null;
            String pattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("user").get("name")), pattern),
                    cb.like(cb.lower(root.get("user").get("userId")), pattern),
                    cb.like(cb.lower(root.get("user").get("email")),pattern)
            );
        };
    }

    public static Specification<GoalEntity> hasStatus(GoalStatus status) {
        return (root, query, cb) -> {
            if (status == null) return null;
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<GoalEntity> createdAfter(LocalDateTime dateFrom) {
        return (root, query, cb) -> {
            if (dateFrom == null) return null;
            return cb.greaterThanOrEqualTo(root.get("createdAt"), dateFrom);
        };
    }

    public static Specification<GoalEntity> createdBefore(LocalDateTime dateTo) {
        return (root, query, cb) -> {
            if (dateTo == null) return null;
            return cb.lessThanOrEqualTo(root.get("createdAt"), dateTo);
        };
    }

    public static Specification<GoalEntity> hasCategoryId(String categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null || categoryId.isBlank()) return null;
            return cb.equal(root.get("category").get("categoryId"), categoryId);
        };
    }

    public static Specification<GoalEntity> isNotDeleted() {
        return (root, query, cb) -> cb.isFalse(root.get("deleted"));
    }


    public static Specification<GoalEntity> hasMinTargetAmount(BigDecimal minAmount) {
        return (root, query, cb) -> {
            if (minAmount == null) return null;
            return cb.greaterThanOrEqualTo(root.get("targetAmount"), minAmount);
        };
    }

    public static Specification<GoalEntity> hasMaxTargetAmount(BigDecimal maxAmount) {
        return (root, query, cb) -> {
            if (maxAmount == null) return null;
            return cb.lessThanOrEqualTo(root.get("targetAmount"), maxAmount);
        };
    }

    public static Specification<GoalEntity> fromFilter(GoalAdminFilter filter) {
        return (root, query, cb) -> {
            // Build predicates
            Predicate predicate = cb.conjunction();

            if (filter.keyword() != null && !filter.keyword().isBlank()) {
                predicate = cb.and(predicate, keywordLike(filter.keyword()).toPredicate(root, query, cb));
            }
            if (filter.goalStatus() != null) {
                predicate = cb.and(predicate, hasStatus(filter.goalStatus()).toPredicate(root, query, cb));
            }
            if (filter.dateFrom() != null) {
                predicate = cb.and(predicate, createdAfter(filter.dateFrom()).toPredicate(root, query, cb));
            }
            if (filter.dateTo() != null) {
                predicate = cb.and(predicate, createdBefore(filter.dateTo()).toPredicate(root, query, cb));
            }
            if (filter.categoryId() != null && !filter.categoryId().isBlank()) {
                predicate = cb.and(predicate, hasCategoryId(filter.categoryId()).toPredicate(root, query, cb));
            }
            if (filter.minAmount() != null) {
                predicate = cb.and(predicate, hasMinTargetAmount(filter.minAmount()).toPredicate(root, query, cb));
            }
            if (filter.maxAmount() != null) {
                predicate = cb.and(predicate, hasMaxTargetAmount(filter.maxAmount()).toPredicate(root, query, cb));
            }

            predicate = cb.and(predicate, isNotDeleted().toPredicate(root, query, cb));

            // Apply sorting
            if (filter.asc()) {
                query.orderBy(cb.asc(root.get("createdAt")));
            } else {
                query.orderBy(cb.desc(root.get("createdAt")));
            }

            return predicate;
        };
    }

}
