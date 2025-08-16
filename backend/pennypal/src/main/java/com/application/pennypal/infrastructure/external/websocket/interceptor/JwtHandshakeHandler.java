//package com.application.pennypal.infrastructure.external.websocket.interceptor;
//
//import com.application.pennypal.application.port.out.service.TokenServicePort;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.HandshakeHandler;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import java.util.Map;
//@RequiredArgsConstructor
//public class JwtHandshakeHandler implements HandshakeInterceptor {
//    private final TokenServicePort tokenServicePort;
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        if (request instanceof ServletServerHttpRequest servletRequest) {
//            HttpServletRequest httpRequest = servletRequest.getServletRequest();
//            Cookie[] cookies = httpRequest.getCookies();
//
//            if (cookies != null) {
//                for (Cookie cookie : cookies) {
//                    if ("accessToken".equals(cookie.getName())) {
//                        String token = cookie.getValue();
//                        String email = tokenServicePort.getUsernameFromToken(token); // Your JWT logic
//                        attributes.put("user", new CustomPrincipal(email));
//                        break;
//                    }
//                }
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//
//    }
//}
