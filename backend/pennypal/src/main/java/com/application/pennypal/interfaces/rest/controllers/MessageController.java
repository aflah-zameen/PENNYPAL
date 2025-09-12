package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.output.notification.NotificationOutputModel;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.infrastructure.adapter.out.message.MessageService;
import com.application.pennypal.infrastructure.persistence.jpa.notification.NotificationRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/private/user/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final GetUser getUser;
    @GetMapping
    public ResponseEntity<List<NotificationOutputModel>> getAllNotifications(HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        List<NotificationOutputModel> messages = messageService.getAllNotificationsForUser(user.userId());
        return ResponseEntity.ok(messages);
    }



    /// helper methods
    private UserDomainDTO getUserFromRequest(HttpServletRequest request, String tokenName){
        String accessToken = extractTokenFromCookie(request,tokenName);
        return getUser.get(accessToken);
    }

    private String extractTokenFromCookie(HttpServletRequest request, String tokenName) {
        if(request.getCookies() != null){
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> tokenName.equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        return null;
    }
}
