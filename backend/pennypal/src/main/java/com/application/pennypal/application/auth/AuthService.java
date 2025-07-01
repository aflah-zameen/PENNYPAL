package com.application.pennypal.application.auth;

import com.application.pennypal.domain.user.valueObject.Roles;
import com.application.pennypal.shared.exception.DuplicateEmailException;
import com.application.pennypal.shared.exception.InvalidRoleException;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.RefreshToken.RefreshTokenRepository;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.RefreshTokenEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper.UserMapper;
import com.application.pennypal.interfaces.rest.dtos.*;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.infrastructure.security.jwt.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepositoryPort userRepository ;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OtpService otpService;
    private final UserMapper userMapper;

    public AuthService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepositoryPort userRepository, RefreshTokenRepository refreshTokenRepository, OtpService otpService, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
//        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.otpService = otpService;
        this.userMapper = userMapper;
    }

    @Transactional
    public JwtData authenticateAndGenerateToken(String email, String password, String ipAddress) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        refreshTokenRepository.deleteByUserId(user.getId());
        return generateTokens(user.getEmail(),ipAddress);
    }

    private RefreshTokenEntity generateRefreshToken(Long userId,String ipAddress){
        Instant expiryDate = Instant.now().plusMillis(24*60*60*1000);
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(userId,expiryDate,ipAddress);
        return refreshTokenRepository.save(refreshTokenEntity);
    }



//    @Transactional
//    public void registerUser( @Valid RegisterRequest registerRequest) {
//        if (userRepository.existsByEmail(registerRequest.getEmail())) {
//            throw new DuplicateEmailException("Email already exists: " + registerRequest.getEmail());
//        }
//        Set<Roles> roles = switch (registerRequest.getRole()) {
//            case "SUPER_ADMIN" -> Set.of(Roles.SUPER_ADMIN, Roles.ADMIN);
//            case "ADMIN" -> Set.of(Roles.ADMIN);
//            case "USER" -> Set.of(Roles.USER);
//            default -> throw new InvalidRoleException("Invalid role: " + registerRequest.getRole());
//        };
//
//        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
//        User user = new User(
//                registerRequest.getName(),
//                registerRequest.getEmail(),
//                encodedPassword,
//                registerRequest.getPhone(),
//                roles
//        );
//        user = userRepository.save(user);
//        otpService.generateAndSendOtp(user.getEmail());
//    }

//    @Transactional
//    public void registerAdmin(AdminRegisterRequest request){
//        if (userRepository.existsByEmail(request.getEmail())) {
//            throw new DuplicateEmailException("Email already exists: " + request.getEmail());
//        }
//        Set<Roles> roles = switch (request.getRole()) {
//            case "SUPER_ADMIN" -> Set.of(Roles.SUPER_ADMIN, Roles.ADMIN);
//            case "ADMIN" -> Set.of(Roles.ADMIN);
//            case "USER" -> Set.of(Roles.USER);
//            default -> throw new InvalidRoleException("Invalid role: " + request.getRole());
//        };
//
//        String encodedPassword = passwordEncoder.encode(request.getPassword());
//        User user = new User(
//                request.getName(),
//                request.getEmail(),
//                encodedPassword,
//                request.getPhone(),
//                roles
//        );
//     userRepository.save(user);
//
//    }

    @Transactional
    public void verifyUser(String email,String otp){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(otpService.validateOtp(email, otp)){
            user.setVerified(true);
            userRepository.save(user);
        }else{
            throw new IllegalArgumentException("Invalid or expired otp");
        }
    }

    @Transactional
    public void resentOtp(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        if(user.isVerified()){
            throw new IllegalArgumentException("User is already verified");
        }

        otpService.resendOtp(email);
    }

    @Transactional
    public void sendOtp(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        otpService.generateAndSendOtp(email);
    }

    public JwtData generateTokens(String email,String ipAddress) throws AuthenticationException{
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
//        String accessToken = jwtUtil.generateAccessToken(user);
        RefreshTokenEntity refreshTokenEntity = generateRefreshToken(user.getId(), ipAddress);
//        return new JwtData(user,accessToken,refreshTokenEntity.getToken());
        return null;
    }

    @Transactional
    public JwtData refreshAccessToken(String refreshToken, String ipAddress) throws RuntimeException{
        RefreshTokenEntity token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if (token.isExpired()) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }
        if (!token.isValidIp(ipAddress)) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Suspicious refresh attempt");
        }
        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        refreshTokenRepository.delete(token);
        RefreshTokenEntity refreshTokenEntity = generateRefreshToken(user.getId(),ipAddress);
//        return new JwtData(user,jwtUtil.generateAccessToken(user),refreshTokenEntity.getToken());
        return null;
    }

    public void updatePassword(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

//    public ResponseEntity<ApiResponse<UserDto>> getUser(String accessToken) throws JwtException,IllegalArgumentException{
////        String email = jwtUtil.getUsernameFromToken(accessToken);
////        User user = userRepository.findByEmail(email)
////                .orElseThrow(() -> new IllegalArgumentException("User is not registered"));
////        UserDto userDto = userMapper.toDto(user);
////        return ResponseEntity.ok(new ApiResponse<>(true,userDto,"User detail fetched successfully"));
//        return null;
//    }

    @Transactional
    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }
}
