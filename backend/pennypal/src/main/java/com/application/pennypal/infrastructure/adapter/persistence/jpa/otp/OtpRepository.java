package com.application.pennypal.infrastructure.adapter.persistence.jpa.otp;

import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity,Long> {
    Optional<OtpEntity> findByEmailAndOtpAndUsedFalseAndExpiresAtAfter(String email, String otp, LocalDateTime now);
    void deleteByExpiresAtBefore(LocalDateTime now);
    List<OtpEntity> findByEmailAndUsedFalse(String email);

}
