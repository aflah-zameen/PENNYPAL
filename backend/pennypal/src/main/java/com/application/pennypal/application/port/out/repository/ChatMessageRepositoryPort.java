package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.domain.chat.ChatMessage;

import java.util.List;

public interface ChatMessageRepositoryPort {
    ChatMessage save(ChatMessage chatMessage);
    List<ChatMessage> findConversion(String userA, String userB, int page, int size);
    void markDelivered(List<String> ids);
}
