package com.application.pennypal.application.port.in.chat;

import com.application.pennypal.application.dto.output.chat.ChatMessageOutputModel;

import java.util.List;

public interface GetConversation {
    List<ChatMessageOutputModel> handle(String userA, String userB, int page, int size);
}