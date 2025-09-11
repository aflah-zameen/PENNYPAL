package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.chat.ChatMessage;
import com.application.pennypal.infrastructure.persistence.jpa.chat.ChatMessageEntity;

public class ChatMessageJpaMapper {
    public static ChatMessageEntity toEntity(ChatMessage chatMessage){
        return new ChatMessageEntity(
                chatMessage.getChatId(),
                chatMessage.getSenderId(),
                chatMessage.getReceiverId(),
                chatMessage.getContent(),
                chatMessage.getSentAt(),
                chatMessage.getStatus(),
                chatMessage.getReplyToMessageId(),
                chatMessage.getMediaUrl()
        );
    }

    public static ChatMessage toDomain(ChatMessageEntity chatMessageEntity){
        return ChatMessage.reconstruct(
                chatMessageEntity.getChatId(),
                chatMessageEntity.getSenderId(),
                chatMessageEntity.getReceiverId(),
                chatMessageEntity.getContent(),
                chatMessageEntity.getSentAt(),
                chatMessageEntity.getStatus(),
                chatMessageEntity.getReplyToMessageId(),
                chatMessageEntity.getMediaUrl(),
                chatMessageEntity.isDeleted()
        );
    }
}
