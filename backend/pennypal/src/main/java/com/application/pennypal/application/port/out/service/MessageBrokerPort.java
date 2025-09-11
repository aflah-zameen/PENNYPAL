package com.application.pennypal.application.port.out.service;

import com.application.pennypal.application.dto.output.notification.NotificationOutputModel;
import com.application.pennypal.domain.LoanCase.LoanCase;
import com.application.pennypal.domain.goal.entity.GoalWithdraw;
import com.application.pennypal.domain.notification.Notification;

import java.math.BigDecimal;

public interface MessageBrokerPort {
    void notifyPrivateUser(Notification notification, String userId);
    void publishGoalWithdrawAdmin(Notification notification,GoalWithdraw goalWithdraw);
    void publishLoanCase(Notification adminNotif, LoanCase loanCase);
}
