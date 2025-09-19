package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.output.coin.CoinBalanceOutputModel;
import com.application.pennypal.application.dto.output.coin.RedemptionHistoryOutputModel;
import com.application.pennypal.application.port.in.coin.RequestCoinRedemption;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.application.port.out.repository.RedemptionRequestRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserCoinAccountRepositoryPort;
import com.application.pennypal.domain.coin.RedemptionRequest;
import com.application.pennypal.domain.coin.UserCoinAccount;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import com.application.pennypal.interfaces.rest.dtos.coin.RedemptionRequestDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/private/user/coins")
@RequiredArgsConstructor
public class CoinController {
    private final GetUser getUser;
    private final UserCoinAccountRepositoryPort userCoinAccountRepositoryPort;
    private final RedemptionRequestRepositoryPort redemptionRequestRepositoryPort;
    private final RequestCoinRedemption requestCoinRedemption;

    @GetMapping("/balance")
    public ResponseEntity<ApiResponse<CoinBalanceOutputModel>> getBalance(HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        UserCoinAccount coinAccount = userCoinAccountRepositoryPort.findByUserId(user.userId())
                .orElse(null);
        if(coinAccount == null){
            // If no coin account exists, return zero balance
            CoinBalanceOutputModel outputModel = new CoinBalanceOutputModel(BigDecimal.ZERO, BigDecimal.ZERO);
            return ResponseEntity.ok(new ApiResponse<>(true, outputModel,"Balance fetched successfully"));
        }
        CoinBalanceOutputModel outputModel = new CoinBalanceOutputModel(
                coinAccount.getBalance(),
                coinAccount.getTotalEarned()
        );
        return ResponseEntity.ok(new ApiResponse<>(true, outputModel,"Balance fetched successfully"));
    }

    @GetMapping("/redemptions")
    public ResponseEntity<ApiResponse<List<RedemptionHistoryOutputModel>>> getHistory(HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        List<RedemptionRequest> requests = redemptionRequestRepositoryPort.getHistory(user.userId());
        List<RedemptionHistoryOutputModel> outputModels = requests.stream()
                .map(redemptionRequest -> new RedemptionHistoryOutputModel(
                        redemptionRequest.getId(),
                        redemptionRequest.getCoinsRedeemed(),
                        redemptionRequest.getRealMoney(),
                        redemptionRequest.getStatus(),
                        redemptionRequest.getCreatedAt(),
                        redemptionRequest.getApprovedAt()
                ))
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(true, outputModels,"Redemption request fetched successfully"));
    }

    @PostMapping("/redeem")
    public ResponseEntity<ApiResponse<RedemptionHistoryOutputModel>> requestRedemption(@Valid @RequestBody RedemptionRequestDTO redemptionRequestDTO,HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        RedemptionHistoryOutputModel outputModel = requestCoinRedemption.execute(user.userId(), redemptionRequestDTO.coinAmount(),redemptionRequestDTO.realMoneyAmount());
        return ResponseEntity.ok(new ApiResponse<>(true, outputModel,"Redemption requested successfully"));
    }


    /// Helper methods
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
