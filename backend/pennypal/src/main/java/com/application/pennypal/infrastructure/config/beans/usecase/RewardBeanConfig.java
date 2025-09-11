package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.port.in.coin.CoinReward;
import com.application.pennypal.application.port.in.reward.*;
import com.application.pennypal.application.port.out.repository.RewardPolicyRepositoryPort;
import com.application.pennypal.application.service.reward.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RewardBeanConfig {
    @Bean
    SetReward setReward(RewardPolicyRepositoryPort rewardPolicyRepositoryPort){
        return new SetRewardService(rewardPolicyRepositoryPort);
    }

    @Bean
    ActivateRewardPolicy activateRewardPolicy(RewardPolicyRepositoryPort rewardPolicyRepositoryPort){
        return new ActivateRewardPolicyService(rewardPolicyRepositoryPort);
    }

    @Bean
    DeactivateRewardPolicy deactivateRewardPolicy(RewardPolicyRepositoryPort rewardPolicyRepositoryPort){
        return new DeactivateRewardPolicyService(rewardPolicyRepositoryPort);
    }

    @Bean
    GetRewardPolicies getRewardPolicies(RewardPolicyRepositoryPort rewardPolicyRepositoryPort){
        return new GetRewardPoliciesService(rewardPolicyRepositoryPort);
    }

    @Bean
    GetRewardAmount getRewardAmount(RewardPolicyRepositoryPort rewardPolicyRepositoryPort){
        return new GetRewardAmountService(rewardPolicyRepositoryPort);
    }

    @Bean
    DeleteRewardPolicy deleteRewardPolicy(RewardPolicyRepositoryPort rewardPolicyRepositoryPort){
        return new DeleteRewardPolicyService(rewardPolicyRepositoryPort);
    }

}
