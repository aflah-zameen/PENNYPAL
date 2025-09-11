package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.port.in.coin.*;
import com.application.pennypal.application.port.out.repository.CoinTransactionRepositoryPort;
import com.application.pennypal.application.port.out.repository.RedemptionRequestRepositoryPort;
import com.application.pennypal.application.port.out.repository.RewardPolicyRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserCoinAccountRepositoryPort;
import com.application.pennypal.application.service.coin.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoinBeanConfig {
    @Bean
    public CoinReward coinReward(UserCoinAccountRepositoryPort userCoinAccountRepositoryPort,
                                 RewardPolicyRepositoryPort rewardPolicyRepositoryPort,
                                 CoinTransactionRepositoryPort coinTransactionRepositoryPort){
        return new CoinRewardService(rewardPolicyRepositoryPort,userCoinAccountRepositoryPort,coinTransactionRepositoryPort);
    }

    @Bean
    public RequestCoinRedemption requestCoinRedemption(RedemptionRequestRepositoryPort redemptionRequestRepositoryPort,
                                                       UserCoinAccountRepositoryPort userCoinAccountRepositoryPort){
        return new RequestCoinRedemptionService(redemptionRequestRepositoryPort,userCoinAccountRepositoryPort);
    }

    @Bean
    public GetRedemptionStats getRedemptionStats(RedemptionRequestRepositoryPort redemptionRequestRepositoryPort){
        return new GetRedemptionStatsService(redemptionRequestRepositoryPort);
    }

    @Bean
    public ApproveRedemptionRequest approveRedemptionRequest(RedemptionRequestRepositoryPort repositoryPort){
        return new ApproveRedemptionRequestService(repositoryPort);
    }

    @Bean
    public RejectRedemptionRequest rejectRedemptionRequest(RedemptionRequestRepositoryPort repositoryPort){
        return new RejectRedemptionRequestService(repositoryPort);
    }

    @Bean
    public GetRedemptionRequests getRedemptionRequests(RedemptionRequestRepositoryPort redemptionRequestRepositoryPort){
        return new GetRedemptionRequestsService(redemptionRequestRepositoryPort);
    }
}
