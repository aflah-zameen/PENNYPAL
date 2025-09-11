package com.application.pennypal.infrastructure.persistence.jpa.chat;

import com.application.pennypal.domain.chat.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity,Long> {
    @Query("""
        select m from ChatMessageEntity m
        where (m.senderId = :userA and m.receiverId = :userB)
           or (m.senderId = :userB and m.receiverId = :userA)
        order by m.sentAt asc, m.id asc
    """)
    List<ChatMessageEntity> findConversation(String userA, String userB, Pageable pageable);

    List<ChatMessageEntity> findAllByChatIdIn(List<String> ids);

    Optional<ChatMessageEntity> findByChatId(String chatId);

    List<ChatMessage> findByReceiverIdOrSenderIdOrderBySentAtDesc(String userId, String userId1);
}
