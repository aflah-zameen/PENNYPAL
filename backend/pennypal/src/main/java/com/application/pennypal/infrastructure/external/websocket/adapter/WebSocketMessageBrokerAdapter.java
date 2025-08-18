package com.application.pennypal.infrastructure.external.websocket.adapter;

import com.application.pennypal.application.dto.output.notification.NotificationOutputModel;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.domain.goal.entity.GoalWithdraw;
import com.application.pennypal.infrastructure.external.websocket.dto.GoalWithdrawMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class WebSocketMessageBrokerAdapter implements MessageBrokerPort {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void notifyPrivateUser(NotificationOutputModel outputModel,String userId) {
        messagingTemplate.convertAndSendToUser(
                userId,
                "/queue/notifications",
                outputModel
        );
    }

    @Override
    public void publishAdmin(GoalWithdraw goalWithdraw, String message) {
        messagingTemplate.convertAndSend("/topic/admin/notifications",new GoalWithdrawMessageDTO(
                message,
                goalWithdraw.getGoalId(),
                LocalDateTime.now()
        ));
    }

//    @Override
//    public void notifyGoalWithdrawalApproval(NotificationOutputModel outputModel, String userId) {
//        messagingTemplate.convertAndSendToUser(
//                userId,
//                "/queue/notifications",
//                outputModel
//        );
//    }

}
