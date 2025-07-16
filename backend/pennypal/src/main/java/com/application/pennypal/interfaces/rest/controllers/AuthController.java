package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.output.auth.LoginResponseOutput;
import com.application.pennypal.application.service.auth.ValidateEmailUniqueness;
import com.application.pennypal.application.usecases.user.*;
import com.application.pennypal.domain.valueObject.TokenPairDTO;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.interfaces.rest.dtos.*;
import com.application.pennypal.interfaces.rest.dtos.auth.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final LoginUser loginUser;
    private final RefreshAccessToken refreshAccessToken;
    private final CreateUser createUser;
    private final SendOtp sendOtp;
    private final VerifyOtp verifyOtp;
    private final GetUser getUser;
    private final UpdatePassword updatePassword;
    private final LogoutUser logoutUser;
    private final ValidateEmailUniqueness validateEmailUniqueness;

    AuthController(LoginUser loginUser,
                   RefreshAccessToken refreshAccessToken,
                   CreateUser createUser,
                   SendOtp sendOtp,
                   VerifyOtp verifyOtp,
                   GetUser getUser,
                   UpdatePassword updatePassword,
                   LogoutUser logoutUser,
                   ValidateEmailUniqueness validateEmailUniqueness){
    this.loginUser = loginUser;
    this.refreshAccessToken = refreshAccessToken;
    this.createUser = createUser;
    this.sendOtp = sendOtp;
    this.verifyOtp = verifyOtp;
    this.getUser  = getUser;
    this.updatePassword = updatePassword;
    this.logoutUser = logoutUser;
    this.validateEmailUniqueness = validateEmailUniqueness;
    }

    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> register(@Valid @ModelAttribute RegisterRequest request, BindingResult result) {
            if(result.hasErrors()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterResponse(false,"Given arguments are not valid."));
            }
            createUser.execute(request.getName(),request.getEmail(),request.getPassword(),
                    request.getPhone(),"USER",request.getProfilePicture());
            return ResponseEntity.ok(new RegisterResponse(true, "User Registered Successfully."));
    }

    @PostMapping("/sent-otp")
    public ResponseEntity<ApiResponse<OtpDTO>> sendOtp(@RequestParam String email){
        LocalDateTime expiresAt = sendOtp.send(email);
        OtpDTO otpDTO = new OtpDTO(expiresAt);
        return ResponseEntity.ok(new OtpResponse(true,otpDTO,"OTP sent successfully"));
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<OtpDTO>> verifyOtp(@Valid @RequestBody OtpRequest otpRequest,HttpServletRequest request,HttpServletResponse response){
        verifyOtp.verify(otpRequest.getEmail(),otpRequest.getOtp(),otpRequest.getContext());
        return ResponseEntity.ok(
                new OtpResponse(true,null,"OTP Verified successfully")
        );
    }

    @PatchMapping("/update-password")
    public ResponseEntity<ApiResponse<String>> updatePassword(@RequestParam String password,@RequestParam String email){
        updatePassword.update(email,password);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Password updated successfully"));
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse<OtpDTO>> resendOtp(@Valid @RequestBody ResendOtpRequest request){
            LocalDateTime expiresAt = sendOtp.resend(request.getEmail());
            OtpDTO otpDTO = new OtpDTO(expiresAt);
            return ResponseEntity.ok(new OtpResponse(true,otpDTO,"OTP resend successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDomainDTO>> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpServletRequest, HttpServletResponse response){

        LoginResponseOutput loginResponseOutput = loginUser.execute(request.getEmail(), request.getPassword(),httpServletRequest.getRemoteAddr());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        setAccessTokenCookie(loginResponseOutput.accessToken(), response);
        setRefreshTokenCookie(loginResponseOutput.refreshToken(), response);
        return ResponseEntity.ok(new LoginResponse(true, loginResponseOutput.user(),"Successfully Login"));
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
    public ResponseEntity<ApiResponse<UserDomainDTO>> getUser(@CookieValue(name = "accessToken",required = false) String accessToken){
        if(accessToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false,null,"Access token is empty."));
        UserDomainDTO user = getUser.get(accessToken);
        return ResponseEntity.ok(new ApiResponse<>(true,user,"User authenticated successfully"));
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
        if (refreshToken != null) {
            logoutUser.execute(refreshToken);
        }

        // Clear access token cookie
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();

        // Clear refresh token cookie
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();

        // Add cleared cookies to response
        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());

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
