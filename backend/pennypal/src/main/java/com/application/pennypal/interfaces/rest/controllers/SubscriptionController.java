package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.output.payment.PaymentResultOutputModel;
import com.application.pennypal.application.dto.output.subscription.PlanOutputModel;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.infrastructure.adapter.out.subscription.SubscriptionInfraService;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import com.application.pennypal.interfaces.rest.dtos.subscription.PlanRequest;
import com.application.pennypal.interfaces.rest.dtos.subscription.PurchaseRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/plans")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionInfraService subscriptionInfraService;
    private final GetUser getUser;

    @PostMapping("/checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody PlanRequest request) {
        try {

            PriceCreateParams productParams = PriceCreateParams.builder()
                    .setUnitAmount(request.price().longValue() * 100)
                    .setCurrency("usd")
                    .setProductData(
                            PriceCreateParams.ProductData.builder()
                                    .setName("PennyPal Plan: " + request.planId()) // or any dynamic name
                                    .build()
                    )
                    .build();
            Price price = Price.create(productParams);

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT) // or PAYMENT for one-time
                    .setSuccessUrl("http://localhost:4200/user/plans?session_id={CHECKOUT_SESSION_ID}&plan_id="+request.planId())
                    .setCancelUrl("http://localhost:4200/user/plans")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setPrice(price.getId()) // This is the Stripe Price ID for the plan
                                    .setQuantity(1L)
                                    .build()
                    )
                    .build();

            Session session = Session.create(params);

            Map<String, String> response = new HashMap<>();
            response.put("sessionId", session.getId());
            return ResponseEntity.ok(response);

        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }


    @GetMapping("")
    public ResponseEntity<ApiResponse<List<PlanOutputModel>>> getPlans(){
        List<PlanOutputModel> outputModels = subscriptionInfraService.getPlans();
        return ResponseEntity.ok(new ApiResponse<>(true,outputModels,"All plans fetched successfully"));
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody PurchaseRequest purchaseRequest, HttpServletRequest servletRequest){
        try {
            Session session = Session.retrieve(purchaseRequest.sessionId());

            if (!"complete".equals(session.getStatus())) {
                return ResponseEntity.badRequest().body(Map.of("error", "Payment not completed"));
            }
            UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
            PaymentResultOutputModel outputModel = subscriptionInfraService.purchasePlan(user.userId(),purchaseRequest.planId());
            return ResponseEntity.ok(Map.of("message", "Purchase successful"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to activate plan: " + e.getMessage()));
        }
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
