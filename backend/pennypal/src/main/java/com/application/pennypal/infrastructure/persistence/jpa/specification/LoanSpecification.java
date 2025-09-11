package com.application.pennypal.infrastructure.persistence.jpa.specification;

import com.application.pennypal.application.port.in.lent.LoanFilterInputModel;
import com.application.pennypal.domain.lend.LoanStatus;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.persistence.jpa.lend.LendingRequestEntity;
import com.application.pennypal.infrastructure.persistence.jpa.lend.LoanEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LoanSpecification {

    public static Specification<LoanEntity> keywordLike(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) return null;
            String pattern = "%" + keyword.toLowerCase() + "%";

            Join<LoanEntity, LendingRequestEntity> requestJoin = root.join("lendingRequest");
            Join<LendingRequestEntity, UserEntity> borrowerJoin = requestJoin.join("borrower");
            Join<LendingRequestEntity, UserEntity> lenderJoin = requestJoin.join("lender");

            return cb.or(
                    cb.like(cb.lower(borrowerJoin.get("name")), pattern),
                    cb.like(cb.lower(borrowerJoin.get("userId")), pattern),
                    cb.like(cb.lower(lenderJoin.get("name")), pattern),
                    cb.like(cb.lower(lenderJoin.get("userId")), pattern),
                    cb.like(cb.lower(root.get("loanId")), pattern),
                    cb.like(cb.function("CAST", String.class, root.get("amount")), pattern)
            );
        };
    }

    public static Specification<LoanEntity> hasStatus(LoanStatus status) {
        return (root, query, cb) -> {
            if (status == null) return null;
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<LoanEntity> createdAfter(LocalDateTime dateFrom) {
        return (root, query, cb) -> {
            if (dateFrom == null) return null;
            return cb.greaterThanOrEqualTo(root.get("createdAt"), dateFrom);
        };
    }

    public static Specification<LoanEntity> createdBefore(LocalDateTime dateTo) {
        return (root, query, cb) -> {
            if (dateTo == null) return null;
            return cb.lessThanOrEqualTo(root.get("createdAt"), dateTo);
        };
    }

    public static Specification<LoanEntity> fromFilter(LoanFilterInputModel filter) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filter.searchTerm() != null && !filter.searchTerm().isBlank()) {
                predicate = cb.and(predicate, keywordLike(filter.searchTerm()).toPredicate(root, query, cb));
            }

            if (filter.status() != null && !filter.status().isBlank()) {
                LoanStatus statusEnum = LoanStatus.valueOf(filter.status());
                predicate = cb.and(predicate, hasStatus(statusEnum).toPredicate(root, query, cb));
            }

            // Optional: Add date range filters if needed
            // predicate = cb.and(predicate, createdAfter(...));
            // predicate = cb.and(predicate, createdBefore(...));

            if ("asc".equalsIgnoreCase(filter.sortOrder())) {
                query.orderBy(cb.asc(root.get("createdAt")));
            } else {
                query.orderBy(cb.desc(root.get("createdAt")));
            }

            return predicate;
        };
    }
}

