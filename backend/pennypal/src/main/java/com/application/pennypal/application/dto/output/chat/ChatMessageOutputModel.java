package com.application.pennypal.application.dto.output.chat;

import jdk.jshell.Snippet;

import java.time.LocalDateTime;

public record ChatMessageOutputModel(
        String chatId,
        String senderId,
        String receiverId,
        String content,
        LocalDateTime sentAt,
        String status,
        boolean isFromUser
) {
}
