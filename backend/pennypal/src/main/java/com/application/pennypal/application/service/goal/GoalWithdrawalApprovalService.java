package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.dto.output.notification.NotificationOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.goal.GoalWithdrawalApproval;
import com.application.pennypal.application.port.out.repository.*;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.domain.goal.entity.Goal;
import com.application.pennypal.domain.goal.entity.GoalWithdraw;
import com.application.pennypal.domain.notification.Notification;
import com.application.pennypal.domain.notification.NotificationType;
import com.application.pennypal.domain.transaction.entity.Transaction;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.valueObject.GoalStatus;
import com.application.pennypal.domain.valueObject.GoalWithdrawRequestStatus;
import com.application.pennypal.domain.valueObject.PaymentMethod;
import com.application.pennypal.domain.valueObject.TransactionType;
import com.application.pennypal.domain.wallet.entity.Wallet;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class GoalWithdrawalApprovalService implements GoalWithdrawalApproval {
    private final GoalWithdrawRepositoryPort goalWithdrawRepositoryPort;
    private final GoalRepositoryPort goalRepositoryPort;
    private final WalletRepositoryPort walletRepositoryPort;
    private final MessageBrokerPort messageBrokerPort;
    private final NotificationRepositoryPort notificationRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    @Override
    public void execute(String withdrawId) {
        GoalWithdraw goalWithdraw = goalWithdrawRepositoryPort.findGoalWithdrawById(withdrawId)
                .orElseThrow(() -> new ApplicationBusinessException("Withdrawal request not found","NOT_FOUND"));
        goalWithdraw = goalWithdraw.updateStatus(GoalWithdrawRequestStatus.APPROVED);
        Goal goal = goalRepositoryPort.getGoalById(goalWithdraw.getGoalId())
                .orElseThrow(() -> new ApplicationBusinessException("Goal not found","NOT_FOUND"));
        goal.setCurrentAmount(BigDecimal.ZERO);
        goal.setStatus(GoalStatus.WITHDRAW_COLLECTED);

        goalWithdrawRepositoryPort.update(goalWithdraw);
        goalRepositoryPort.update(goal,goal.getGoalId());

        Wallet wallet = walletRepositoryPort.findByUserId(goal.getUserId())
                .orElseThrow(() -> new ApplicationBusinessException("Wallet not found","NOT_FOUND"));
        wallet = wallet.creditAmount(goalWithdraw.getAmount());
        walletRepositoryPort.update(wallet);

        /// Create Transaction
        Transaction transaction = Transaction.create(
                wallet.getUserId(),
                null,
                null,
                goalWithdraw.getAmount(),
                TransactionType.WALLET,
                "GOAL AMOUNT WITHDRAWAL",
                null,
                PaymentMethod.CARD,
                LocalDate.now(),
                false,
                null,
                wallet.getUserId(),
                null
        );
        transactionRepositoryPort.save(transaction);

        /// Create a notification about the approval
        Notification notification = Notification.create(
                goal.getUserId(),
                "Your withdrawal request of " + goalWithdraw.getAmount() + " has been approved.",
                NotificationType.GOAL_WITHDRAW,
                null,
                false
        );
        notificationRepositoryPort.save(notification);
        /// Notify user about the approval
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
