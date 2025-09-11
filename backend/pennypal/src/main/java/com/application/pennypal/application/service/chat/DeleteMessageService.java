package com.application.pennypal.application.service.chat;

import com.application.pennypal.application.dto.output.chat.ChatMessageOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.chat.DeleteMessage;
import com.application.pennypal.application.port.out.repository.ChatMessageRepositoryPort;
import com.application.pennypal.domain.chat.ChatMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteMessageService implements DeleteMessage {
    private final ChatMessageRepositoryPort chatMessageRepositoryPort;
    @Override
    public ChatMessageOutputModel execute(String userId, String messageId) {
        chatMessageRepositoryPort.deleteMessage(messageId);
        ChatMessage chatMessage = chatMessageRepositoryPort.findByChatId(messageId)
                .orElseThrow(() -> new ApplicationBusinessException("Chat not found","NOT_FOUND"));
        return new ChatMessageOutputModel(
                chatMessage.getChatId(),
                chatMessage.getSenderId(),
                chatMessage.getReceiverId(),
                chatMessage.getContent(),
                chatMessage.getSentAt(),
                chatMessage.getStatus().getValue(),
                chatMessage.getSenderId().equals(userId),
                chatMessage.getReplyToMessageId(),
                chatMessage.getMediaUrl(),
                chatMessage.isDeleted()
        );
    }
}
