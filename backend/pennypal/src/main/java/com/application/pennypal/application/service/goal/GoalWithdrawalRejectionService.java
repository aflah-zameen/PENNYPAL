package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.dto.output.notification.NotificationOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.goal.GoalWithdrawalRejection;
import com.application.pennypal.application.port.out.repository.GoalRepositoryPort;
import com.application.pennypal.application.port.out.repository.GoalWithdrawRepositoryPort;
import com.application.pennypal.application.port.out.repository.NotificationRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.domain.goal.entity.Goal;
import com.application.pennypal.domain.goal.entity.GoalWithdraw;
import com.application.pennypal.domain.notification.Notification;
import com.application.pennypal.domain.notification.NotificationType;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.valueObject.GoalStatus;
import com.application.pennypal.domain.valueObject.GoalWithdrawRequestStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GoalWithdrawalRejectionService implements GoalWithdrawalRejection {
    private final GoalRepositoryPort goalRepositoryPort;
    private final GoalWithdrawRepositoryPort goalWithdrawRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final MessageBrokerPort messageBrokerPort;
    private final NotificationRepositoryPort notificationRepositoryPort;

    @Override
    public void execute(String withdrawID) {
        GoalWithdraw goalWithdraw = goalWithdrawRepositoryPort.findGoalWithdrawById(withdrawID)
                .orElseThrow(() -> new ApplicationBusinessException("Goal withdraw not found","NOT_FOUND"));
        goalWithdraw = goalWithdraw.updateStatus(GoalWithdrawRequestStatus.REJECTED);
        goalWithdrawRepositoryPort.update(goalWithdraw);

        Goal goal = goalRepositoryPort.getGoalById(goalWithdraw.getGoalId())
                .orElseThrow(() -> new ApplicationBusinessException("Goal not found","NOT_FOUND"));
        goal.setStatus(GoalStatus.WITHDRAW_REJECTED);
        goalRepositoryPort.update(goal,goal.getGoalId());

        /// Create a notification about the approval
        Notification notification = Notification.create(
                goal.getUserId(),
                "Your withdrawal request of " + goalWithdraw.getAmount() + " has been rejected.",
                NotificationType.GOAL_WITHDRAW,
                null,
                false
        );
        notificationRepositoryPort.save(notification);

        /// notify user
        User user = userRepositoryPort.findByUserId(goal.getUserId())
                .orElseThrow(() -> new ApplicationBusinessException("User not found","USER_NOT_FOUND"));
        messageBrokerPort.notifyGoalWithdrawalApproval(new NotificationOutputModel(
                notification.getId(),
                notification.getMessage(),
                false,
                notification.getTimeStamp(),
                notification.getType(),
                notification.getActionURL()
        ),user.getEmail());
    }
}
