package com.application.pennypal.application.service.chat;

import com.application.pennypal.application.port.in.chat.MarkMessageDelivered;
import com.application.pennypal.application.port.out.repository.ChatMessageRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class MarkMessageDeliveredService implements MarkMessageDelivered {
    private final ChatMessageRepositoryPort chatMessageRepositoryPort;
    @Override
    public void handle(List<String> messageIds) {
        chatMessageRepositoryPort.markDelivered(messageIds);
    }
}
