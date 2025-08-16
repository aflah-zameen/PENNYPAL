package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.output.notification.NotificationOutputModel;
import com.application.pennypal.application.port.in.notification.GetAllAdminNotifications;
import com.application.pennypal.application.port.in.notification.GetAllUserNotification;
import com.application.pennypal.application.port.in.notification.MarkNotificationAsRead;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/private/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final MarkNotificationAsRead markNotificationAsRead;
    private final GetUser getUser;
    private final GetAllUserNotification getAllUserNotification;
    private final GetAllAdminNotifications getAllAdminNotifications;

    @PutMapping("/read")
    public ResponseEntity<ApiResponse<?>> markAsRead(@RequestParam String notificationId, HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        markNotificationAsRead.execute(user.userId(), notificationId);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Marked the notification as read"));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<NotificationOutputModel>>> getAllUserNotifications(HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<NotificationOutputModel> outputModels = getAllUserNotification.execute(user.userId());
        return ResponseEntity.ok(new ApiResponse<>(true,outputModels,"All notifications fetched successfully"));
    }

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<List<NotificationOutputModel>>> getAllAdminNotifications(){
        List<NotificationOutputModel> outputModels = getAllAdminNotifications.execute();
        return ResponseEntity.ok(new ApiResponse<>(true,outputModels,"All notifications fetched successfully"));
    }

    /// helper methods
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
