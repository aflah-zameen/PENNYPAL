package com.application.pennypal.application.port.in.reward;

import com.application.pennypal.application.dto.output.reward.RewardPolicyOutputModel;

public interface ActivateRewardPolicy {
    RewardPolicyOutputModel execute(String id);
}
