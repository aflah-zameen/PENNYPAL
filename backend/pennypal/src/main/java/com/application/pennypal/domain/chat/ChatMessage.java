package com.application.pennypal.domain.chat;

import lombok.Getter;
import lombok.Setter;

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
    private final String replyToMessageId;   // For replies
    private final String mediaUrl;           // For uploaded images/files
    @Setter
    private boolean deleted = false;   // Soft delete

    private ChatMessage(
            String chatId,
            String senderId,
            String receiverId,
            String content,
            LocalDateTime sentAt,
            MessageStatus status,
            String replyToMessageId,
            String mediaUrl,
            boolean deleted
    ){
        this.chatId = chatId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.sentAt = sentAt;
        this.status = status;
        this.replyToMessageId = replyToMessageId;
        this.mediaUrl = mediaUrl;
        this.deleted = deleted;
    }

    public static ChatMessage create(
            String senderId,
            String receiverId,
            String content,
            String replyToMessageId,
            String mediaUrl
            ){
        String id = "MSG_"+ UUID.randomUUID();
        return new ChatMessage(
                id,
                senderId,
                receiverId,
                content,
                LocalDateTime.now(),
                MessageStatus.SEND,
                replyToMessageId,
                mediaUrl,
                false
        );
    }

    public static ChatMessage reconstruct(
            String chatId,
            String senderId,
            String receiverId,
            String content,
            LocalDateTime sentAt,
            MessageStatus status,
            String replyToMessageId,
            String mediaUrl,
            boolean deleted
    ){
        return new ChatMessage(
                chatId,
                senderId,
                receiverId,
                content,
                sentAt,
                status,
                replyToMessageId,
                mediaUrl,
                deleted
        );
    }

    /// Helper methods
    public ChatMessage updateStatus(MessageStatus status){
        this.status = status;
        return this;
    }
}
