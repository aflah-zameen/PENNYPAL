package com.application.pennypal.application.service.chat;

import com.application.pennypal.application.dto.input.chat.SendMessageCommand;
import com.application.pennypal.application.dto.output.chat.ChatMessageOutputModel;
import com.application.pennypal.application.port.in.chat.SendPrivateMessage;
import com.application.pennypal.application.port.out.repository.ChatMessageRepositoryPort;
import com.application.pennypal.application.port.out.service.FileUploadPort;
import com.application.pennypal.domain.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@RequiredArgsConstructor
public class SendPrivateMessageService implements SendPrivateMessage {
    private final ChatMessageRepositoryPort chatMessageRepositoryPort;
    private final FileUploadPort fileUploadPort;
    @Override
    public ChatMessageOutputModel handle(String senderId, SendMessageCommand cmd){

        String url  = null;
        if(cmd.file() != null){
            byte[] decodedBytes = Base64.getDecoder().decode(cmd.file().base64());
            MultipartFile file = new MockMultipartFile(
                    cmd.file().filename(),
                    cmd.file().filename(),
                    cmd.file().mediaType(),
                    decodedBytes
            );
            url = fileUploadPort.uploadFile(file);
        }

        var message = ChatMessage.create(
                senderId,
                cmd.receiverId(),
                cmd.content(),
                cmd.replyToMessageId(),
                url
        );

        var saved = chatMessageRepositoryPort.save(message);

        return new ChatMessageOutputModel(
                saved.getChatId(),
                saved.getSenderId(),
                saved.getReceiverId(),
                saved.getContent(),
                saved.getSentAt(),
                saved.getStatus().getValue(),
                false,
                saved.getReplyToMessageId(),
                saved.getMediaUrl(),
                saved.isDeleted()
        );
    }
}
