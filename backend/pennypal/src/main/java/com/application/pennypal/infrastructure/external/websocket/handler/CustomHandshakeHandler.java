package com.application.pennypal.infrastructure.external.websocket.handler;

import com.application.pennypal.application.port.out.service.TokenServicePort;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
@RequiredArgsConstructor
public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    private final TokenServicePort tokenServicePort;
    @Override
    protected Principal determineUser(
            ServerHttpRequest request,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest httpRequest = servletRequest.getServletRequest();
            String token = extractAccessToken(httpRequest);

            if (token != null && tokenServicePort.isValid(token)) {
                String username = tokenServicePort.getUsernameFromToken(token);
                return () -> username; // Principal with getName() = email
            }
        }
        return null;
    }

    private String extractAccessToken(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("accessToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}

