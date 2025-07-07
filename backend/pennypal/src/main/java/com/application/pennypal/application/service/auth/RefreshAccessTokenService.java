package com.application.pennypal.application.service.auth;

import com.application.pennypal.application.port.RefreshTokenServicePort;
import com.application.pennypal.application.port.TokenServicePort;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.application.usecases.user.RefreshAccessToken;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.user.valueObject.RefreshTokenInfo;
import com.application.pennypal.domain.user.valueObject.TokenPairDTO;
import com.application.pennypal.shared.exception.InvalidRefreshTokenException;
import com.application.pennypal.shared.exception.IpAddressMismatchException;
import com.application.pennypal.shared.exception.RefreshTokenExpiredException;
import com.application.pennypal.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import java.time.Instant;

@RequiredArgsConstructor
public class RefreshAccessTokenService implements RefreshAccessToken {

    private final RefreshTokenServicePort refreshTokenServicePort;
    private final UserRepositoryPort userRepositoryPort;
    private final TokenServicePort tokenServicePort;

    @Override
    public TokenPairDTO execute(String refreshToken, String ipAddress) {
        RefreshTokenInfo refreshTokenInfo = refreshTokenServicePort.findByToken(refreshToken)
                .orElseThrow(()->new InvalidRefreshTokenException("User in the refresh token is invalid."));
        if(refreshTokenInfo.expiryDate().isBefore(Instant.now())){
            throw new RefreshTokenExpiredException("Refresh token has expired");
        }
        if(!refreshTokenInfo.ipAddress().equals(ipAddress.trim())){
            throw new IpAddressMismatchException("Ip address is mismatched.");
        }
        User user = userRepositoryPort.findById(refreshTokenInfo.userId())
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        if(!user.isActive()){
            throw new LockedException("User is not a active");
        }
        if(!user.isVerified()){
            throw new DisabledException("User is not verified");
        }

        String newRefreshToken = refreshTokenServicePort.generateRefreshToken(refreshTokenInfo.userId(),ipAddress);
        String newAccessToken =  tokenServicePort.generateAccessToken(user);
        return new TokenPairDTO(newRefreshToken,newAccessToken);
    }
}
