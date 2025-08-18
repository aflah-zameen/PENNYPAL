package com.application.pennypal.application.dto.input.chat;

public record SendMessageCommand(String receiverId, String content) {
}
