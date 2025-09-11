package com.application.pennypal.infrastructure.persistence.jpa.coin.repository;

import com.application.pennypal.domain.coin.RedemptionRequest;
import com.application.pennypal.infrastructure.persistence.jpa.coin.entity.RedemptionRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RedemptionRequestRepository extends JpaRepository<RedemptionRequestEntity,String>, JpaSpecificationExecutor<RedemptionRequestEntity> {
    List<RedemptionRequestEntity> findAllByUserIdOrderByCreatedAtDesc(String userId);
    @Query("""
        SELECT
            COALESCE(SUM(r.coinsRedeemed), 0),
            COALESCE(SUM(r.realMoney), 0),
            COUNT(CASE WHEN r.status = 'PENDING' THEN 1 END)
        FROM RedemptionRequestEntity r
    """)
    Object fetchRawRedemptionStats();


    List<RedemptionRequest> findAllByOrderByCreatedAtDesc();
}
