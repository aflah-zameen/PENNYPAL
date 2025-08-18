package com.application.pennypal.application.service.chat;

import com.application.pennypal.application.dto.input.chat.SendMessageCommand;
import com.application.pennypal.application.dto.output.chat.ChatMessageOutputModel;
import com.application.pennypal.application.port.in.chat.SendPrivateMessage;
import com.application.pennypal.application.port.out.repository.ChatMessageRepositoryPort;
import com.application.pennypal.domain.chat.ChatMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendPrivateMessageService implements SendPrivateMessage {
    private final ChatMessageRepositoryPort chatMessageRepositoryPort;
    @Override
    public ChatMessageOutputModel handle(String senderId, SendMessageCommand cmd){
        var message = ChatMessage.create(
                senderId,
                cmd.receiverId(),
                cmd.content()
        );

        var saved = chatMessageRepositoryPort.save(message);

        return new ChatMessageOutputModel(
                saved.getChatId(),
                saved.getSenderId(),
                saved.getReceiverId(),
                saved.getContent(),
                saved.getSentAt(),
                saved.getStatus().getValue(),
                false

        );
    }
}
