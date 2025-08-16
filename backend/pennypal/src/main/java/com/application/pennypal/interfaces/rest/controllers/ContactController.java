package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.output.user.ContactOutputModel;
import com.application.pennypal.application.port.in.user.GetContacts;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/private/user")
@RequiredArgsConstructor
public class ContactController {
    private final GetUser getUser;
    private final GetContacts getContacts;


    @GetMapping("/contacts")
    public ResponseEntity<ApiResponse<List<ContactOutputModel>>> getContacts(HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        List<ContactOutputModel> outputModels = getContacts.execute(user.userId());
        return ResponseEntity.ok(new ApiResponse<>(true, outputModels,"Fetched successfully"));
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
    private LocalDate parseDate(String dateStr) {
        try {
            // Define the formatter for MM/yy format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");

            // Parse the string to a YearMonth object first
            java.time.YearMonth yearMonth = java.time.YearMonth.parse(dateStr, formatter);

            // Convert to LocalDate by setting the day to 1 (or any default day)
            return yearMonth.atDay(1);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + e.getMessage());
            return null;
        }
    }
}
