package com.application.pennypal.infrastructure.external.websocket.contorller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocketMessageController {
    @MessageMapping("/ping")
    public void handlePing(Principal principal) {
        System.out.println("✅ WebSocket user: " + principal.getName());
    }

}
