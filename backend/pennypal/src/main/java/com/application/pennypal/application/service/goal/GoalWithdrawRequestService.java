package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.dto.input.goal.GoalWithdrawRequestInputModel;
import com.application.pennypal.application.dto.output.notification.NotificationOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.goal.GoalWithdrawRequest;
import com.application.pennypal.application.port.out.repository.NotificationRepositoryPort;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.application.port.out.repository.GoalRepositoryPort;
import com.application.pennypal.application.port.out.repository.GoalWithdrawRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.domain.goal.entity.Goal;
import com.application.pennypal.domain.goal.entity.GoalWithdraw;
import com.application.pennypal.domain.notification.Notification;
import com.application.pennypal.domain.notification.NotificationType;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.valueObject.GoalStatus;
import com.application.pennypal.domain.valueObject.GoalWithdrawRequestStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GoalWithdrawRequestService implements GoalWithdrawRequest {
    private final GoalWithdrawRepositoryPort goalWithdrawRepositoryPort;
    private final GoalRepositoryPort goalRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final MessageBrokerPort messageBrokerPort;
    private final NotificationRepositoryPort notificationRepositoryPort;

    @Override
    public void execute(GoalWithdrawRequestInputModel inputModel) {
        Goal goal = goalRepositoryPort.getGoalById(inputModel.goalId())
                .orElseThrow(() -> new  ApplicationBusinessException("Goal cannot be found","NOT_FOUND"));
        User user = userRepositoryPort.findByUserId(inputModel.userId())
                .orElseThrow(() -> new ApplicationBusinessException("User not found","USER_NOT_FOUND"));
        if(!goal.getUserId().equals(inputModel.userId())){
            throw new ApplicationBusinessException("Wrong user","WRONG_AUTHOR");
        }

        if(!goal.getStatus().equals(GoalStatus.COMPLETED)){
            throw new ApplicationBusinessException("Goal is not completed","WRONG_ACTION");
        }

        /// Save Goal withdraw
        GoalWithdraw goalWithdraw = GoalWithdraw.create(
                goal.getGoalId(),
                user.getEmail(),
                goal.getCurrentAmount(),
                GoalWithdrawRequestStatus.PENDING
        );
        goalWithdrawRepositoryPort.save(goalWithdraw);

        /// Update goal status and save
        goal.setStatus(GoalStatus.WITHDRAW_PENDING);
        goalRepositoryPort.update(goal,goal.getGoalId());

        /// Save the notifications
        String adminMessage = String.format(
                "User %s requested to withdraw ₹%,.2f from goal \"%s\".",
                user.getName(), goalWithdraw.getAmount(), goal.getTitle()
        );
        String userMessage = String.format(
                "Your withdrawal request for ₹%,.2f from goal \"%s\" is pending approval.",
                goalWithdraw.getAmount(), goal.getTitle()
        );
        notificationRepositoryPort.save(Notification.create(
                null,
                adminMessage,
                NotificationType.GOAL_WITHDRAW,
                null,
                true
        ));
        Notification notification = notificationRepositoryPort.save(Notification.create(
                user.getUserId(),
                userMessage,
                NotificationType.GOAL_WITHDRAW,
                null,
                false
        ));

        /// Notify admin
        messageBrokerPort.notifyWithdrawRequestAdmin(goalWithdraw,adminMessage);

        /// Notify User
        messageBrokerPort.notifyWithdrawRequestUser(new NotificationOutputModel(
                notification.getId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getTimeStamp(),
                notification.getType(),
                notification.getActionURL()
        ),user.getEmail());

    }
}
