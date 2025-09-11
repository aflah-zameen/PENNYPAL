package com.application.pennypal.application.port.in.chat;

import com.application.pennypal.application.dto.output.chat.ChatMessageOutputModel;

public interface DeleteMessage {
    ChatMessageOutputModel execute(String userId,String messageId);
}
