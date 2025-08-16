package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.input.card.CardInputModel;
import com.application.pennypal.application.dto.input.card.CardSpendingInputModel;
import com.application.pennypal.application.dto.input.card.ExpenseFilterInputModel;
import com.application.pennypal.application.dto.output.card.*;
import com.application.pennypal.application.dto.output.transaction.TransactionOutputModel;
import com.application.pennypal.application.port.in.card.*;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.domain.card.valueObject.CardType;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import com.application.pennypal.interfaces.rest.dtos.card.AddCardRequestDTO;
import com.application.pennypal.interfaces.rest.dtos.card.CardSummaryResponseDTO;
import com.application.pennypal.interfaces.rest.dtos.card.CardTransactionResponseDTO;
import com.application.pennypal.interfaces.rest.dtos.card.CardUserResponseDTO;
import com.application.pennypal.interfaces.rest.mappers.CardDtoMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/private/user")
@RequiredArgsConstructor
public class CardController {
    private final GetUser getUser;
    private final GetCard getCard;
    private final AddCard addCard;
    private final GetCardSpendingOverview getCardSpendingOverview;
    private final GetCardExpenseOverview getCardExpenseOverview;
    private final GetCardTransaction getCardTransaction;
    private final GetPaymentMethods getPaymentMethods;

    @GetMapping("/fetch-cards-summary")
    public ResponseEntity<ApiResponse<List<CardSummaryResponseDTO>>> fetchCards(HttpServletRequest servletRequest){
        UserDomainDTO user= getUserFromRequest(servletRequest,"accessToken");
        List<CardSummaryOutputModel> outputModels = getCard.getAllSummary(user.userId());
        List<CardSummaryResponseDTO> responseDTOS = outputModels.stream()
                .map(CardDtoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(true,responseDTOS,"Fetch all cards successfully"));
    }

    @PostMapping("add-card")
    public ResponseEntity<ApiResponse<CardUserResponseDTO>> addCard(@Valid @RequestBody AddCardRequestDTO requestDTO,HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        CardInputModel inputModel = new CardInputModel(
                requestDTO.name(),
                requestDTO.holder(),
                requestDTO.number(),
                parseDate(requestDTO.expiry()),
                CardType.valueOf(requestDTO.type().toUpperCase()),
                requestDTO.balance(),
                requestDTO.gradient(),
                requestDTO.pin()
        );
        CardOutputModel outputModel = addCard.execute(user.userId(),inputModel);
        CardUserResponseDTO responseDTO = CardDtoMapper.toResponse(outputModel);
        return ResponseEntity.ok(new ApiResponse<>(true,responseDTO,"Card added successfully"));
    }

    @GetMapping("/card-spending-chart")
    public ResponseEntity<ApiResponse<CardSpendingOutputModel>> getCardSpendingChart(@RequestParam String cardId,
                                                                                     @RequestParam(name = "range") String filterValue){
        CardSpendingOutputModel outputModel = getCardSpendingOverview.execute(new CardSpendingInputModel(cardId,filterValue));
        return ResponseEntity.ok(new ApiResponse<>(true,outputModel,"Card spending fetched successfully"));
    }

    @GetMapping("card-expense-overview")
    public ResponseEntity<ApiResponse<List<CardExpenseOverviewOutputModel>>> getCardExpenseOverview(@RequestParam String cardId,
                                                                                                    @RequestParam(defaultValue = "monthly") String range,
                                                                                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                                                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate){
        List<CardExpenseOverviewOutputModel> outputModel = getCardExpenseOverview.execute(cardId,new ExpenseFilterInputModel(range,fromDate,toDate));
        return ResponseEntity.ok(new ApiResponse<>(true,outputModel,"Card expense fetched successfully"));
    }

    @GetMapping("card-recent-transactions")
    public ResponseEntity<ApiResponse<List<CardTransactionResponseDTO>>> getRecentTransaction(@RequestParam String cardId,
                                                                                              @RequestParam int size){
        List<TransactionOutputModel> outputModels = getCardTransaction.getRecent(cardId,size);
        List<CardTransactionResponseDTO> responseDTOS = outputModels.stream()
                .map(output -> {
                    return new CardTransactionResponseDTO(
                            output.transactionId(),
                            output.title(),
                            output.category() != null ?output.category().name() : null,
                            output.transactionDate(),
                            output.amount(),
                            output.transactionType().getValue().toLowerCase());
                })
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(true,responseDTOS,"Transaction fetched successfully"));
    }

    @GetMapping("/payment-methods")
    public ResponseEntity<ApiResponse<List<PaymentMethodOutputModel>>> getPaymentMethods(HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        List<PaymentMethodOutputModel> outputModels = getPaymentMethods.execute(user.userId());
        return ResponseEntity.ok(new ApiResponse<>(true, outputModels,"Fetched successfully"));
    }


    /// helper methods
    private UserDomainDTO getUserFromRequest(HttpServletRequest request,String tokenName){
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
