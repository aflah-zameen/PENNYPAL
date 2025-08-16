package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.input.wallet.AddWalletInputModel;
import com.application.pennypal.application.dto.output.transaction.TransactionOutputModel;
import com.application.pennypal.application.dto.output.wallet.WalletOutputModel;
import com.application.pennypal.application.dto.output.wallet.WalletStatsOutputModel;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.application.port.in.wallet.GetWallet;
import com.application.pennypal.application.port.in.wallet.GetWalletStats;
import com.application.pennypal.application.port.in.wallet.GetWalletTransactions;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.infrastructure.adapter.out.wallet.AddMoneyWalletInfraService;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import com.application.pennypal.interfaces.rest.dtos.wallet.WalletTransactionResponseDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/private/user")
@RequiredArgsConstructor
public class WalletController {
    private final GetUser getUser;
    private final GetWallet getWallet;
    private final GetWalletStats getWalletStats;
    private final GetWalletTransactions getWalletTransactions;
    private final AddMoneyWalletInfraService addMoneyWalletInfraService;

    @GetMapping("/wallet")
    public ResponseEntity<ApiResponse<WalletOutputModel>> getWallet(HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        WalletOutputModel walletOutputModel = getWallet.execute(user.userId());
        return ResponseEntity.ok(new ApiResponse<>(true,walletOutputModel,"Wallet details fetched successfully"));
    }

    @GetMapping("/wallet/stats")
    public ResponseEntity<ApiResponse<WalletStatsOutputModel>> getWalletStats(HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        WalletStatsOutputModel outputModel = getWalletStats.execute(user.userId());
        return ResponseEntity.ok(new ApiResponse<>(true,outputModel,"Fetched wallet successfully"));
    }

    @GetMapping("/wallet/transactions")
    public ResponseEntity<ApiResponse<List<WalletTransactionResponseDTO>>> getWalletTransactions(HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<TransactionOutputModel> outputModels = getWalletTransactions.execute(user.userId());
        List<WalletTransactionResponseDTO> walletTransactions = outputModels.stream()
                .map(transaction -> new WalletTransactionResponseDTO(
                        transaction.transactionId(),
                        transaction.transferFromUserId() != null ? "debit":"credit",
                        transaction.amount(),
                        transaction.description(),
                        transaction.createdAt(),
                        transaction.category() != null ? transaction.category().name():null,
                        transaction.category() != null ? transaction.category().icon() : null
                ))
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(true, walletTransactions, "Fetched transaction history"));
    }

    @PostMapping("/wallet/add")
    public ResponseEntity<ApiResponse<?>> addMoneyToWallet(@RequestBody AddWalletInputModel inputModel, HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        addMoneyWalletInfraService.setAddMoneyToWallet(user.userId(),inputModel);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Money added to wallet successfully"));
    }


    /// Helper methods
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
