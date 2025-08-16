package com.application.pennypal.application.service.auth;

import com.application.pennypal.application.exception.usecase.auth.InvalidOtpApplicationException;
import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.repository.WalletRepositoryPort;
import com.application.pennypal.application.port.out.service.OtpCachePort;
import com.application.pennypal.application.port.in.user.VerifyOtp;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.wallet.entity.Wallet;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class VerifyOtpService implements VerifyOtp {
    private final OtpCachePort otpCachePort;
    private final UserRepositoryPort userRepositoryPort;
    private final WalletRepositoryPort walletRepositoryPort;
    @Override
    @Transactional
    public void verify(String email, String otp) {
        String tokenGenerated = otpCachePort.getOtp(email)
                .orElseThrow(() -> new UserNotFoundApplicationException("The given email has no otp or expired"));
        if(!otp.equals(tokenGenerated)){
            throw new InvalidOtpApplicationException("OTP is not valid");
        }
        otpCachePort.deleteOtp(email);
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found exception"));
        User verifiedUser = user.verify();
        userRepositoryPort.update(verifiedUser);

        Wallet wallet = Wallet.create(
                user.getUserId(),
                BigDecimal.ZERO
        );

        walletRepositoryPort.save(wallet);

    }
}
