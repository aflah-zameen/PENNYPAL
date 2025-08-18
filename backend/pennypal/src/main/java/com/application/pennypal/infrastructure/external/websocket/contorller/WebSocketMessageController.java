package com.application.pennypal.infrastructure.external.websocket.contorller;

import com.application.pennypal.application.dto.input.chat.SendMessageCommand;
import com.application.pennypal.application.dto.output.chat.ChatMessageOutputModel;
import com.application.pennypal.application.dto.output.notification.NotificationOutputModel;
import com.application.pennypal.application.port.in.chat.SendPrivateMessage;
import com.application.pennypal.application.port.out.repository.NotificationRepositoryPort;
import com.application.pennypal.application.port.out.service.MessageBrokerPort;
import com.application.pennypal.domain.notification.Notification;
import com.application.pennypal.domain.notification.NotificationType;
import com.application.pennypal.infrastructure.external.websocket.custom.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class WebSocketMessageController {
    private final SendPrivateMessage sendPrivateMessage;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageBrokerPort messageBrokerPort;
    private final NotificationRepositoryPort notificationRepositoryPort;

    /// Client sends to /app/chat.send
    @MessageMapping("/chat.send")
    public void send(SendMessageCommand cmd,Principal principal){
        String senderId="";
        String senderName="";
        if(principal instanceof AuthenticatedUser){
            senderId = principal.getName();
            senderName = ((AuthenticatedUser) principal).getUsername();
        }
        ChatMessageOutputModel outputModel = sendPrivateMessage.handle(senderId,cmd);

        /// Deliver to receiver's personal queue
        messagingTemplate.convertAndSendToUser(
                outputModel.receiverId(),
                "/queue/messages",
                outputModel
        );

        ChatMessageOutputModel responseOutputModel = new ChatMessageOutputModel(
                outputModel.chatId(),
                outputModel.senderId(),
                outputModel.receiverId(),
                outputModel.content(),
                outputModel.sentAt(),
                outputModel.status(),
                true
        );
        /// Optionally echo back to sender (to update their UI immediately)
        messagingTemplate.convertAndSendToUser(
                outputModel.senderId(),
                "/queue/messages",
                responseOutputModel
        );

        String message = "Who have a new message from "+senderName;

        /// notify messages for receiver
        Notification notification = Notification.create(
                cmd.receiverId(),
                message,
                NotificationType.CHAT,
                null,
                false
        );
        notificationRepositoryPort.save(notification);
        messageBrokerPort.notifyPrivateUser(notification, cmd.receiverId());

    }

}
