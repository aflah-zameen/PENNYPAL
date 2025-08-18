package com.application.pennypal.application.port.out.service;

import com.application.pennypal.application.dto.output.notification.NotificationOutputModel;
import com.application.pennypal.domain.goal.entity.GoalWithdraw;

import java.math.BigDecimal;

public interface MessageBrokerPort {
    void notifyPrivateUser(NotificationOutputModel outputModel,String userId);
    void publishAdmin(GoalWithdraw goalWithdraw,String message);
//    void notifyGoalWithdrawalApproval(NotificationOutputModel outputModel,String email);
}
