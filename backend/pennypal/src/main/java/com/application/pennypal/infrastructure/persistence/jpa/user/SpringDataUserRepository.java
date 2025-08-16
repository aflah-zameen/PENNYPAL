package com.application.pennypal.infrastructure.persistence.jpa.user;

import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.user.valueObject.Roles;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserEntity,Long>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String Email);

    Optional<UserEntity> findByUserId(String adminId);

    @Query("""
    SELECT u FROM UserEntity u
    WHERE u.userId <> :requestedUserId
    AND u.active = true
    AND u.verified = true
    AND :role MEMBER OF u.roles
""")
    List<UserEntity> findAllContacts(@Param("requestedUserId") String userId,
                                     @Param("role") Roles role);
}
