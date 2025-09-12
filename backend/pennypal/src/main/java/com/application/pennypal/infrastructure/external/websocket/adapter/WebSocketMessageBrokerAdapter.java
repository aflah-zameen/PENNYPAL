package com.application.pennypal.infrastructure.external.websocket.adapter;

import com.application.pennypal.application.dto.output.lend.LoanCaseOutputModel;
import com.application.pennypal.application.dto.output.notification.NotificationOutputModel;
import com.application.pennypal.application.mappers.lent.LoanCaseApplicationMapper;
import com.application.pennypal.application.port.out.repository.NotificationRepositoryPort;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.domain.LoanCase.LoanCase;
import com.application.pennypal.domain.goal.entity.GoalWithdraw;
import com.application.pennypal.domain.notification.Notification;
import com.application.pennypal.infrastructure.external.websocket.dto.GoalWithdrawMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class WebSocketMessageBrokerAdapter implements MessageBrokerPort {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepositoryPort notificationRepositoryPort;
    private final LoanCaseApplicationMapper loanCaseApplicationMapper;

    @Override
    public void notifyPrivateUser(Notification notification, String userId) {
        /// Save notification db
        notification = notificationRepositoryPort.save(notification);

        NotificationOutputModel outputModel = new NotificationOutputModel(
                notification.getId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getTimeStamp().toInstant(ZoneOffset.UTC),
                notification.getType(),
                notification.getActionURL()
        );
        messagingTemplate.convertAndSendToUser(
                userId,
                "/queue/notifications",
                outputModel
        );
    }

    @Override
    public void publishGoalWithdrawAdmin(Notification notification,GoalWithdraw goalWithdraw) {
        /// Save notification db
        notification = notificationRepositoryPort.save(notification);

        NotificationOutputModel outputModel = new NotificationOutputModel(
                notification.getId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getTimeStamp().toInstant(ZoneOffset.UTC),
                notification.getType(),
                notification.getActionURL()
        );
        messagingTemplate.convertAndSend("/topic/admin/notifications",new GoalWithdrawMessageDTO(
                notification.getMessage(),
                goalWithdraw.getGoalId(),
                LocalDateTime.now()
        ));
    }

    @Override
    public void publishLoanCase(Notification notification, LoanCase loanCase) {
        notification = notificationRepositoryPort.save(notification);

        NotificationOutputModel outputModel = new NotificationOutputModel(
                notification.getId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getTimeStamp().toInstant(ZoneOffset.UTC),
                notification.getType(),
                notification.getActionURL()
        );

        messagingTemplate.convertAndSend("/topic/admin/notifications",loanCaseApplicationMapper.toOutput(loanCase));
    }

}
