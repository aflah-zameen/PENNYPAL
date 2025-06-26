package com.application.pennypal.infrastructure.adapter.persistence.jpa.user;

import com.application.pennypal.domain.user.valueObject.Roles;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserEntity,Long>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String Email);
}
