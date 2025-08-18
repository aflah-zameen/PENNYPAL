package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.output.chat.ChatMessageOutputModel;
import com.application.pennypal.application.port.in.chat.GetConversation;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/private/user/chat")
@RequiredArgsConstructor
public class ChatController {
    private final GetConversation getConversation;
    private final GetUser getUser;

    @GetMapping("/history/{otherUserId}")
    public List<ChatMessageOutputModel> history(@PathVariable String otherUserId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "50")int size,
                                                HttpServletRequest servletRequest
                                                ){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        return getConversation.handle(user.userId(),otherUserId,page,size);
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
