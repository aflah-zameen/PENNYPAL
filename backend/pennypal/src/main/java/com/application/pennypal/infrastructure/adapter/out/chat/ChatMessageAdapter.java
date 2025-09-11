package com.application.pennypal.infrastructure.adapter.out.chat;

import com.application.pennypal.application.port.out.repository.ChatMessageRepositoryPort;
import com.application.pennypal.domain.chat.ChatMessage;
import com.application.pennypal.domain.chat.MessageStatus;
import com.application.pennypal.infrastructure.persistence.jpa.chat.ChatMessageEntity;
import com.application.pennypal.infrastructure.persistence.jpa.chat.ChatMessageRepository;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.ChatMessageJpaMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatMessageAdapter implements ChatMessageRepositoryPort {
    private final ChatMessageRepository  chatMessageRepository;

    @Override
    @Transactional
    public ChatMessage save(ChatMessage chatMessage) {
        var entity = ChatMessageJpaMapper.toEntity(chatMessage);
        entity =  chatMessageRepository.save(entity);
        return ChatMessageJpaMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public List<ChatMessage> findConversion(String userA, String userB, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return chatMessageRepository.findConversation(userA,userB,pageable).stream()
                .map(ChatMessageJpaMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void markDelivered(List<String> ids) {
        if (ids == null || ids.isEmpty()) return;
        var list = chatMessageRepository.findAllByChatIdIn(ids);
        list.forEach(e -> e.updateStatus(MessageStatus.DELIVERED));
        chatMessageRepository.saveAll(list);
    }

    @Override
    public Optional<ChatMessage> findByChatId(String chatId) {
        return chatMessageRepository.findByChatId(chatId).map(ChatMessageJpaMapper::toDomain);
    }

    @Override
    public List<ChatMessage> findAllByUserId(String userId) {
        return chatMessageRepository.findByReceiverIdOrSenderIdOrderBySentAtDesc(userId,userId);
    }

    @Override
    public void deleteMessage(String chatId) {
        chatMessageRepository.findByChatId(chatId).ifPresent(msg -> {
            msg.setDeleted(true); // soft delete
            chatMessageRepository.save(msg);
        });
    }
}
