package com.application.pennypal.application.port.in.chat;

import com.application.pennypal.application.dto.input.chat.SendMessageCommand;
import com.application.pennypal.application.dto.output.chat.ChatMessageOutputModel;
import com.application.pennypal.domain.chat.ChatMessage;

public interface SendPrivateMessage {
    ChatMessageOutputModel handle(String senderId, SendMessageCommand cmd);
}
