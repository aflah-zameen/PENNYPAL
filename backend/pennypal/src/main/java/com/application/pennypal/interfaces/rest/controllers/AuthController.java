package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.output.auth.LoginResponseOutput;
import com.application.pennypal.application.dto.output.user.RegisterOutputModel;
import com.application.pennypal.application.dto.output.user.UserOutputModel;
import com.application.pennypal.application.port.in.auth.ResetPasswordVerification;
import com.application.pennypal.application.port.in.auth.SendVerificationEmail;
import com.application.pennypal.application.port.in.auth.VerifyEmailWithToken;
import com.application.pennypal.application.port.in.user.*;
import com.application.pennypal.application.port.out.repository.UserCoinAccountRepositoryPort;
import com.application.pennypal.application.service.auth.ValidateEmailUniqueness;
import com.application.pennypal.domain.valueObject.TokenPairDTO;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.interfaces.rest.dtos.*;
import com.application.pennypal.interfaces.rest.dtos.auth.*;
import com.application.pennypal.interfaces.rest.exception.auth.TokenNotFoundInterfaceException;
import com.application.pennypal.interfaces.rest.mappers.UserDtoMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.Arrays;

@RestController
@RequestMapping("/api/public/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LoginUser loginUser;
    private final RefreshAccessToken refreshAccessToken;
    private final CreateUser createUser;
    private final ReSendOtp sendOtp;
    private final VerifyOtp verifyOtp;
    private final GetUser getUser;
    private final ResetPassword resetPassword;
    private final LogoutUser logoutUser;
    private final ValidateEmailUniqueness validateEmailUniqueness;
    private final VerifyEmailWithToken verifyEmailWithToken;
    private final SendVerificationEmail sendVerificationEmail;
    private final ResetPasswordVerification resetPasswordVerification;
    private final UserCoinAccountRepositoryPort userCoinAccountRepositoryPort;

    /// Admin email verification
    @PatchMapping("/admin/verify-email-token")
    public ResponseEntity<ApiResponse<String>> verifyAdminEmail(@RequestParam(name = "token") String token){
        if(token == null || token.trim().isBlank())
            throw new TokenNotFoundInterfaceException("Token must not be empty");
        verifyEmailWithToken.execute(token);
        return ResponseEntity.ok(new ApiResponse<>(true,"Admin verified successfully","Completed"));
    }

    @PostMapping("/user-register")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @ModelAttribute RegisterRequestDTO requestDTO) {
            RegisterOutputModel outputModel = createUser.execute(UserDtoMapper.toInput(requestDTO));
            RegisterResponseDTO responseDTO = new RegisterResponseDTO(
                    outputModel.id(),
                    outputModel.email(),
                    outputModel.expiry().toInstant(ZoneOffset.UTC)
            );
            return ResponseEntity.ok(new RegisterResponse(true,responseDTO, "User Registered Successfully."));
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<OtpDTO>> verifyOtp(@Valid @RequestBody OtpRequest otpRequest,HttpServletRequest request,HttpServletResponse response){
        verifyOtp.verify(otpRequest.getEmail(),otpRequest.getOtp());
        return ResponseEntity.ok(new OtpResponse(true,null,"OTP Verified successfully")
        );
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<ApiResponse<?>> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO requestDTO){
        resetPassword.reset(requestDTO.email(),requestDTO.password(),requestDTO.verificationToken());
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Password updated successfully"));
    }

    @PostMapping("/forget-password")
    public ResponseEntity<ApiResponse<?>> forgetPassword(@RequestParam("email") String email){
        resetPasswordVerification.execute(email);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Verification mail is send to your email"));
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse<Object>> resendOtp(@RequestParam("email") String email){
           sendOtp.send(email);
            return ResponseEntity.ok(new ApiResponse<>(true,null,"OTP resend successfully"));
    }

    @PostMapping("/admin/resend-verification-email")
    public ResponseEntity<ApiResponse<Object>> resendVerificationEmail(@RequestParam("email") String email){
        sendVerificationEmail.execute(email);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Verification mail send successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDTO>> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpServletRequest, HttpServletResponse response){

        LoginResponseOutput loginResponseOutput = loginUser.execute(request.getEmail(), request.getPassword(),httpServletRequest.getRemoteAddr());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        setAccessTokenCookie(loginResponseOutput.accessToken(), response);
        setRefreshTokenCookie(loginResponseOutput.refreshToken(), response);
        BigDecimal coinBalance = userCoinAccountRepositoryPort.getCoins(loginResponseOutput.user().userId());

        UserResponseDTO responseDTO = new UserResponseDTO(
                loginResponseOutput.user().userId(),
                loginResponseOutput.user().userName(),
                loginResponseOutput.user().email(),
                loginResponseOutput.user().roles(),
                loginResponseOutput.user().phone(),
                coinBalance,
                loginResponseOutput.user().active(),
                loginResponseOutput.user().verified(),
                loginResponseOutput.user().created(),
                loginResponseOutput.user().profileURL(),
                loginResponseOutput.accessToken()
        );
        return ResponseEntity.ok(new LoginResponse(true, responseDTO,"Successfully Login"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<String>> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken,
                                          HttpServletRequest request, HttpServletResponse response) {
        if (refreshToken == null) {
            return ResponseEntity.status(400).body(new ApiResponse<>(false,null,"Refresh token is not given"));
        }
        TokenPairDTO tokens = refreshAccessToken.execute(refreshToken,request.getRemoteAddr());
        setAccessTokenCookie(tokens.accessToken(), response);
        setRefreshTokenCookie(tokens.refreshToken(), response);
        return ResponseEntity.ok(new ApiResponse<>(true,"Refresh token is refreshed","Refreshed the token"));
    }


    @GetMapping("/authenticate")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUser(@CookieValue(name = "accessToken",required = false) String accessToken){
        if(accessToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false,null,"Access token is empty."));
        UserDomainDTO user = getUser.get(accessToken);
        BigDecimal coinBalance = userCoinAccountRepositoryPort.getCoins(user.userId());

        UserResponseDTO responseDTO = new UserResponseDTO(
                user.userId(),
                user.userName(),
                user.email(),
                user.roles(),
                user.phone(),
                coinBalance,
                user.active(),
                user.verified(),
                user.created(),
                user.profileURL(),
                null
        );
        return ResponseEntity.ok(new ApiResponse<>(true,responseDTO,"User authenticated successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request, HttpServletResponse response) {
        if(request.getCookies() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(true,null,""));
        }
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst().orElseThrow(() -> new RuntimeException("Refresh token is not available"))
                .getValue();
        Cookie accessTokenCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("accessToken"))
                .findFirst().orElse(null);
        String accessToken = null;
        if(accessTokenCookie != null){
            accessToken = accessTokenCookie.getValue();
        }
        if (refreshToken != null) {
            logoutUser.execute(refreshToken,accessToken);
        }

        // Clear access token cookie
//        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", "")
//                .httpOnly(true)
//                .secure(true)
//                .sameSite("Strict")
//                .path("/")
//                .maxAge(0)
//                .build();
//
//        // Clear refresh token cookie
//        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", "")
//                .httpOnly(true)
//                .secure(true)
//                .sameSite("Strict")
//                .path("/")
//                .maxAge(0)
//                .build();
//
//        // Add cleared cookies to response
//        response.addHeader("Set-Cookie", accessTokenCookie.toString());
//        response.addHeader("Set-Cookie", refreshTokenCookie.toString());

        return ResponseEntity.ok().body(new ApiResponse<>(true,null,"Logout successfully"));
    }

    @GetMapping("/check-email")
    public ResponseEntity<ApiResponse<Boolean>> checkEmailUniqueness(@RequestParam(name = "email") String email){
        try{
            validateEmailUniqueness.validate(email);
            return ResponseEntity.ok(new ApiResponse<>(true,true,"Email is available"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(false,false,"Email Already Exist"));
        }
    }




    private void setAccessTokenCookie(String accessToken, HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite","Lax");
        cookie.setPath("/");
        cookie.setMaxAge(15 * 60);
        response.addCookie(cookie);
    }

    private void setRefreshTokenCookie(String refreshToken, HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite","Lax");
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
    }

}
