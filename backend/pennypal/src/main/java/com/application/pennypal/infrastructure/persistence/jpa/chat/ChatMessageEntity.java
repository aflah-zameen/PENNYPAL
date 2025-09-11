package com.application.pennypal.infrastructure.persistence.jpa.chat;

import com.application.pennypal.domain.chat.MessageStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ChatMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,updatable = false,unique = true)
    private String chatId;

    @Column(nullable = false)
    private String senderId;

    @Column(nullable = false)
    private String receiverId;

    @Column(nullable=false, length = 4000)
    private String content;

    @Column(nullable=false)
    private LocalDateTime sentAt;

    @Column(nullable=false)
    private MessageStatus status;

    private String replyToMessageId;   // For replies
    private String mediaUrl;           // For uploaded images/files
    @Setter
    private boolean deleted = false;   // Soft delete

    public ChatMessageEntity(
            String chatId,
            String senderId,
            String receiverId,
            String content,
            LocalDateTime sentAt,
            MessageStatus status,
            String replyToMessageId,
            String mediaUrl
    ){
        this.chatId = chatId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.sentAt = sentAt;
        this.status = status;
        this.replyToMessageId = replyToMessageId;
        this.mediaUrl = mediaUrl;
    }

    /// update status
    public ChatMessageEntity updateStatus(MessageStatus status){
        this.status = status;
        return this;
    }



}
