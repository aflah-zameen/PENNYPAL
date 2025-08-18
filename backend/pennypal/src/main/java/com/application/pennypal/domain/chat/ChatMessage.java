package com.application.pennypal.domain.chat;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ChatMessage {
    private final String chatId;
    private final String senderId;
    private final String receiverId;
    private final String content;
    private final LocalDateTime sentAt;
    private MessageStatus status;

    private ChatMessage(
            String chatId,
            String senderId,
            String receiverId,
            String content,
            LocalDateTime sentAt,
            MessageStatus status
    ){
        this.chatId = chatId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.sentAt = sentAt;
        this.status = status;
    }

    public static ChatMessage create(
            String senderId,
            String receiverId,
            String content){
        String id = "MSG_"+ UUID.randomUUID();
        return new ChatMessage(
                id,
                senderId,
                receiverId,
                content,
                LocalDateTime.now(),
                MessageStatus.SEND
        );
    }

    public static ChatMessage reconstruct(
            String chatId,
            String senderId,
            String receiverId,
            String content,
            LocalDateTime sentAt,
            MessageStatus status
    ){
        return new ChatMessage(
                chatId,
                senderId,
                receiverId,
                content,
                sentAt,
                status
        );
    }

    /// Helper methods
    public ChatMessage updateStatus(MessageStatus status){
        this.status = status;
        return this;
    }
}
