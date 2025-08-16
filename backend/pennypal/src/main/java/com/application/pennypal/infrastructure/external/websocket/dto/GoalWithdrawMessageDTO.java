package com.application.pennypal.infrastructure.external.websocket.dto;

import com.application.pennypal.domain.goal.entity.GoalWithdraw;

import java.time.LocalDateTime;

public record GoalWithdrawMessageDTO(
        String message,
        String goalId,
        LocalDateTime timeStamp) {
}
