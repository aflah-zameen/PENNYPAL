package com.application.pennypal.infrastructure.external.websocket.interceptor;

import com.application.pennypal.application.port.out.service.TokenServicePort;
import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//@Component
//@RequiredArgsConstructor
//public class AuthChannelInterceptor implements ChannelInterceptor {
//    private final TokenServicePort jwtService;
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//
//        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            // Read JWT from cookies
//            String token = extractTokenFromCookies(accessor);
//            if (token != null && jwtService.isValid(token)) {
//                String username = jwtService.getUsernameFromToken(token);
//                accessor.setUser(new UsernamePasswordAuthenticationToken(username, null, List.of()));
//            }
//        }
//        return message;
//    }
//
//    private String extractTokenFromCookies(StompHeaderAccessor accessor) {
//        List<String> cookieHeaders = accessor.getNativeHeader("cookie");
//        if (cookieHeaders != null) {
//            for (String cookieHeader : cookieHeaders) {
//                for (String cookie : cookieHeader.split(";")) {
//                    String[] parts = cookie.trim().split("=");
//                    if (parts[0].equals("accessToken")) {
//                        return parts[1];
//                    }
//                }
//            }
//        }
//        return null;
//    }
//}
