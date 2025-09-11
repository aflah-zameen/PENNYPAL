package com.application.pennypal.infrastructure.adapter.out.coin;

import com.application.pennypal.application.dto.input.coin.RedemptionFilterInputModel;
import com.application.pennypal.application.dto.output.coin.PaginatedRedemptionRequest;
import com.application.pennypal.application.dto.output.coin.PaginationRedemptionInfo;
import com.application.pennypal.application.dto.output.coin.RedemptionHistoryOutputModel;
import com.application.pennypal.application.dto.output.coin.RedemptionStatsOutputModel;
import com.application.pennypal.application.port.out.repository.RedemptionRequestRepositoryPort;
import com.application.pennypal.domain.coin.RedemptionRequest;
import com.application.pennypal.domain.coin.RedemptionRequestStatus;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.coin.entity.RedemptionRequestEntity;
import com.application.pennypal.infrastructure.persistence.jpa.coin.repository.RedemptionRequestRepository;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.CoinJpaMapper;
import com.application.pennypal.infrastructure.persistence.jpa.user.SpringDataUserRepository;
import com.application.pennypal.interfaces.rest.dtos.coin.AdminRedemptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RedemptionRequestRepositoryAdapter implements RedemptionRequestRepositoryPort {
    private final RedemptionRequestRepository redemptionRequestRepository;
    private final SpringDataUserRepository userRepository;
    @Override
    public List<RedemptionRequest> getHistory(String userId) {
        return redemptionRequestRepository.findAllByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(CoinJpaMapper::toDomain)
                .toList();
    }

    @Override
    public RedemptionRequest save(RedemptionRequest redemptionRequest) {
        RedemptionRequestEntity entity = CoinJpaMapper.toEntity(redemptionRequest);
        entity = redemptionRequestRepository.save(entity);
        return CoinJpaMapper.toDomain(entity);
    }

    @Override
    public RedemptionStatsOutputModel getStats() {
        Object[] result = (Object[]) redemptionRequestRepository.fetchRawRedemptionStats();

        BigDecimal totalCoins;
        if (result[0] instanceof BigDecimal) {
            totalCoins = (BigDecimal) result[0];
        } else if (result[0] instanceof Number) {
            totalCoins = BigDecimal.valueOf(((Number) result[0]).doubleValue());
        } else {
            totalCoins = BigDecimal.ZERO;
        }

        BigDecimal totalMoney;
        if (result[1] instanceof BigDecimal) {
            totalMoney = (BigDecimal) result[1];
        } else if (result[1] instanceof Number) {
            totalMoney = BigDecimal.valueOf(((Number) result[1]).doubleValue());
        } else {
            totalMoney = BigDecimal.ZERO;
        }

        long pendingCount = result[2] instanceof Number ? ((Number) result[2]).longValue() : 0L;

        return new RedemptionStatsOutputModel(totalCoins, totalMoney, (float) pendingCount);
    }





    @Override
    public Optional<RedemptionRequest> findById(String redemptionId) {
        return redemptionRequestRepository.findById(redemptionId).map(CoinJpaMapper::toDomain);
    }

    @Override
    public PaginatedRedemptionRequest getAll(RedemptionFilterInputModel inputModel) {

        int page = inputModel.page();
        int itemsPerPage = inputModel.size();
        String status = inputModel.status();
//        String search = inputModel.search();
        LocalDateTime dateFrom = inputModel.dateFrom();
        LocalDateTime dateTo = inputModel.dateTo();

        Pageable pageable = PageRequest.of(page - 1, itemsPerPage, Sort.by(Sort.Direction.DESC, "createdAt"));

        List<Specification<RedemptionRequestEntity>> specs = new ArrayList<>();

        if (status != null && !status.equalsIgnoreCase("ALL")) {
            specs.add((root, query, cb) ->
                    cb.equal(root.get("status"), RedemptionRequestStatus.valueOf(status))
            );
        }

//        if (search != null && !search.isBlank()) {
//            specs.add((root, query, cb) -> cb.or(
//                    cb.like(cb.lower(root.get("userName")), "%" + search.toLowerCase() + "%"),
//                    cb.like(cb.lower(root.get("userEmail")), "%" + search.toLowerCase() + "%")
//            ));
//        }

        if (dateFrom != null) {
            specs.add((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("createdAt"), dateFrom)
            );
        }

        if (dateTo != null) {
            specs.add((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("createdAt"), dateTo)
            );
        }

        Specification<RedemptionRequestEntity> combinedSpec = specs.stream()
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction()); // match all if no filters

        Page<RedemptionRequestEntity> pageResult = redemptionRequestRepository.findAll(combinedSpec, pageable);

        List<RedemptionRequest> requests = pageResult.getContent().stream()
                .map(CoinJpaMapper::toDomain)
                .toList();

        PaginationRedemptionInfo pagination = new PaginationRedemptionInfo(
                page,
                pageResult.getTotalPages(),
                pageResult.getTotalElements(),
                itemsPerPage
        );

        List<AdminRedemptionResponse> outputModels = requests.stream()
                .map(request ->{
                    UserEntity user = userRepository.findByUserId(request.getUserId())
                            .orElseThrow(() -> new InfrastructureException("User not found","NOT_FOUND"));
                    return new AdminRedemptionResponse(
                            request.getId(),
                            user.getUserId(),
                            user.getName(),
                            user.getEmail(),
                            request.getCoinsRedeemed(),
                            request.getRealMoney(),
                            request.getStatus().name(),
                            request.getCreatedAt(),
                            request.getApprovedAt(),
                            request.getApprovedBy()
                    );
                })
                .toList();

        return new PaginatedRedemptionRequest(outputModels, pagination);
    }

}
