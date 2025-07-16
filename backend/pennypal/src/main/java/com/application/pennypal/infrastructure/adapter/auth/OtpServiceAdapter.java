package com.application.pennypal.infrastructure.adapter.auth;

import com.application.pennypal.application.port.EmailSendPort;
import com.application.pennypal.application.port.OtpServicePort;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.domain.entity.User;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.OtpEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.otp.OtpRepository;
import com.application.pennypal.shared.exception.OtpInvalidException;
import com.application.pennypal.shared.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class OtpServiceAdapter implements OtpServicePort {
    private final int otpLength;
    private final String sender;
    private final Duration otpExpiration;
    private final OtpRepository otpRepository;
    private final EmailSendPort emailSendPort;
    private final UserRepositoryPort userRepositoryPort;
    OtpServiceAdapter(@Value("${otp.length}") int otpLength,
                      @Value("${otp.sender}") String sender,
                      @Value("${otp.expiration-minutes}")Duration expiration,
                      OtpRepository otpRepository,
                      EmailSendPort emailSendPort,
                      UserRepositoryPort userRepositoryPort
                      ){
        this.otpLength = otpLength;
        this.otpExpiration = expiration;
        this.sender = sender;
        this.otpRepository = otpRepository;
        this.emailSendPort = emailSendPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public LocalDateTime generateOtpAndSend(String email) {
        String otp = generateOtp();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = LocalDateTime.now().plus(otpExpiration);
        OtpEntity otpEntity = new OtpEntity(email,otp,createdAt,expiresAt);
        otpRepository.save(otpEntity);

        String body = "This your OTP for validation.\nOTP CODE : "+otp;
        String subject = "OTP VALIDATION";
        emailSendPort.sendEmail(email,sender,body,subject);
        return expiresAt;
    }

    @Override
    public void validateOtp(String email,String otp,String context) {
        LocalDateTime now = LocalDateTime.now();
        Optional<OtpEntity> optionalOtp = otpRepository.findByEmailAndOtpAndUsedFalseAndExpiresAtAfter(email,otp,now);
        if(optionalOtp.isEmpty()){
            throw new OtpInvalidException("OTP is not valid or expired");
        }
        OtpEntity otpEntity = optionalOtp.get();
        otpEntity.setUsed(true);
        otpRepository.save(otpEntity);
        if(context.equals("EmailProfileUpdate")){
            return;
        }        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if(context.equals("register")){
            user = user.verify();
        }
        userRepositoryPort.save(user);
    }

    @Override
    public LocalDateTime resentOtp(String email) {
        if(otpRepository.findByEmailAndUsedFalse(email).isEmpty()){
            throw new OtpInvalidException("No valid otp in db for for resend");
        }

        invalidateExistingOtps(email);

        String otp = generateOtp();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plus(otpExpiration);
        OtpEntity otpEntity = new OtpEntity(email,otp,now,expiresAt);
        otpRepository.save(otpEntity);
        String body = "This your OTP for validation.\nOTP CODE : "+otp;
        String subject = "OTP VALIDATION";
        emailSendPort.sendEmail(email,sender,body,subject);
        return expiresAt;
    }

    private void invalidateExistingOtps(String email) {
        otpRepository.findByEmailAndUsedFalse(email)
                .forEach(otpEntity -> {
                    otpEntity.setUsed(true);
                    otpRepository.save(otpEntity);
                });
    }

    private String generateOtp(){
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<otpLength;i++){
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}
