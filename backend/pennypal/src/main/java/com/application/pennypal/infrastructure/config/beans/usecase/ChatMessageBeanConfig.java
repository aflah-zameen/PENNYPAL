package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.port.in.chat.GetConversation;
import com.application.pennypal.application.port.in.chat.MarkMessageDelivered;
import com.application.pennypal.application.port.in.chat.SendPrivateMessage;
import com.application.pennypal.application.port.out.repository.ChatMessageRepositoryPort;
import com.application.pennypal.application.service.chat.GetConversationService;
import com.application.pennypal.application.service.chat.MarkMessageDeliveredService;
import com.application.pennypal.application.service.chat.SendPrivateMessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatMessageBeanConfig {
    @Bean
    public SendPrivateMessage sendPrivateMessage(ChatMessageRepositoryPort messageRepositoryPort){
        return new SendPrivateMessageService(messageRepositoryPort);
    }

    @Bean
    public GetConversation getConversation(ChatMessageRepositoryPort messageRepositoryPort){
        return new GetConversationService(messageRepositoryPort);
    }

    @Bean
    public MarkMessageDelivered markMessageDelivered(ChatMessageRepositoryPort messageRepositoryPort){
        return new MarkMessageDeliveredService(messageRepositoryPort);
    }
}
