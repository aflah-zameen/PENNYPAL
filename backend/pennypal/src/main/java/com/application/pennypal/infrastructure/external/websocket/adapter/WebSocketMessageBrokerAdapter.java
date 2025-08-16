package com.application.pennypal.infrastructure.external.websocket.adapter;

import com.application.pennypal.application.dto.output.notification.NotificationOutputModel;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.domain.goal.entity.GoalWithdraw;
import com.application.pennypal.infrastructure.external.websocket.dto.GoalWithdrawMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class WebSocketMessageBrokerAdapter implements MessageBrokerPort {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void notifyWithdrawRequestUser(NotificationOutputModel outputModel,String email) {
        System.out.println("Sending notification to user: " + email);
        messagingTemplate.convertAndSendToUser(
                email,
                "/queue/notifications",
                outputModel
        );
    }

    @Override
    public void notifyWithdrawRequestAdmin(GoalWithdraw goalWithdraw, String message) {
        messagingTemplate.convertAndSend("/topic/admin/notifications",new GoalWithdrawMessageDTO(
                message,
                goalWithdraw.getGoalId(),
                LocalDateTime.now()
        ));
    }

    @Override
    public void notifyGoalWithdrawalApproval(NotificationOutputModel outputModel, String email) {
        messagingTemplate.convertAndSendToUser(
                email,
                "/queue/notifications",
                outputModel
        );
    }

}
