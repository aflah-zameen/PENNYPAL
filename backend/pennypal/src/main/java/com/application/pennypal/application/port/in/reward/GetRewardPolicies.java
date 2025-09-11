package com.application.pennypal.application.port.in.reward;

import com.application.pennypal.application.dto.output.reward.RewardPolicyOutputModel;

import java.util.List;

public interface GetRewardPolicies {
    List<RewardPolicyOutputModel> execute();
}
