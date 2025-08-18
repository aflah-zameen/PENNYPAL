package com.application.pennypal.infrastructure.external.websocket.handler;

import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.TokenServicePort;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.external.websocket.custom.AuthenticatedUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
@RequiredArgsConstructor
public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    private final TokenServicePort tokenServicePort;
    private final UserRepositoryPort userRepositoryPort;
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
                User user = userRepositoryPort.findByEmail(username)
                        .orElseThrow(() -> new InfrastructureException("User not found","NOT_FOUND"));

                return new AuthenticatedUser(user.getUserId(),user.getEmail(),user.getName()); // Principal with getName() = email
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

