package com.application.pennypal.application.dto.input.chat;

import org.springframework.web.multipart.MultipartFile;

public record SendMessageCommand(String receiverId, String content, String replyToMessageId,   // ✅ optional
                                 FileDto file ) {
}
