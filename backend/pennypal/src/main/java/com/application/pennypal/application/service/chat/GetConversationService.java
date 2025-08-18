package com.application.pennypal.application.service.chat;

import com.application.pennypal.application.dto.output.chat.ChatMessageOutputModel;
import com.application.pennypal.application.port.in.chat.GetConversation;
import com.application.pennypal.application.port.out.repository.ChatMessageRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetConversationService implements GetConversation {
    private final ChatMessageRepositoryPort chatMessageRepositoryPort;

    public List<ChatMessageOutputModel> handle(String userA,String userB,int page, int size){
        return chatMessageRepositoryPort.findConversion(userA,userB,page,size).stream()
                .map( m -> new ChatMessageOutputModel(
                        m.getChatId(),
                        m.getSenderId(),
                        m.getReceiverId(),
                        m.getContent(),
                        m.getSentAt(),
                        m.getStatus().getValue(),
                        m.getSenderId().equals(userA)
                ))
                .toList();
    }
}
