package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.output.transaction.SpendSummaryOutputModel;
import com.application.pennypal.application.dto.output.transaction.TransactionOutputModel;
import com.application.pennypal.application.port.in.transaction.GetSpendSummary;
import com.application.pennypal.application.port.in.transaction.GetSpendTransactions;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import com.application.pennypal.interfaces.rest.dtos.transaction.SpendSummary;
import com.application.pennypal.interfaces.rest.dtos.transaction.TransactionResponseDTO;
import com.application.pennypal.interfaces.rest.mappers.TransactionDtoMapper;
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
public class  SpendController {

    private final GetSpendTransactions getSpendTransactions;
    private final GetUser getUser;
    private final GetSpendSummary getSpendSummary;

    @GetMapping("/spend-summary")
    public ResponseEntity<ApiResponse<SpendSummaryOutputModel>> getSpendSummary(HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        SpendSummaryOutputModel outputModel = getSpendSummary.execute(user.userId());
        return ResponseEntity.ok(new ApiResponse<>(true,outputModel,"Spend summary Fetched successfully"));
    }

    @GetMapping("/category-spending")
    public ResponseEntity<?> getCategorySpending(){
        return ResponseEntity.ok(null);
    }

    @GetMapping("/all-spend-transactions")
    public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> getAllSpendTransactions(HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        List<TransactionOutputModel> outputModels = getSpendTransactions.execute(user.userId());
        List<TransactionResponseDTO> responseDTOS = outputModels.stream()
                .map(TransactionDtoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(true,responseDTOS,"Spend transaction Fetched successfully"));
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
