//package com.application.pennypal.infrastructure.external.websocket.handler;
//
//import org.springframework.lang.NonNull;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class PresenceWebSocketHandler extends TextWebSocketHandler {
//    private final Map<String, WebSocketSession> activeSessions = new ConcurrentHashMap<>();
//
//    @Override
//    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
//        String userId = getUserId(session);
//        activeSessions.put(userId, session);
//        broadcastStatus(userId, true);
//    }
//
//    @Override
//    public void afterConnectionClosed(@NonNull WebSocketSession session,@NonNull CloseStatus status) throws Exception {
//        String userId = getUserId(session);
//        activeSessions.remove(userId);
//        broadcastStatus(userId, false);
//    }
//
//    private void broadcastStatus(String userId, boolean isOnline) {
//        String message = String.format(
//                "[{\"userId\":\"%s\", \"online\":%b}]", userId, isOnline
//        );
//
//        activeSessions.values().forEach(session -> {
//            try {
//                session.sendMessage(new TextMessage(message));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//
//    private String getUserId(WebSocketSession session) {
//        // You can extract userId from session attributes or query params
//        return session.getUri().getQuery().split("=")[1]; // Example: ?userId=123
//    }
//}
