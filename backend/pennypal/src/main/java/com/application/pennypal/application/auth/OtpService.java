package com.application.pennypal.application.auth;

import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.OtpEntity;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.otp.OtpRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpService {
    private final OtpRepository otpRepository;
    private final EmailService emailService;
    private final int otpLength;
    private final Duration otpExpirationMinutes;
    private final String sender;

    public OtpService(OtpRepository otpRepository, EmailService emailService,
                        @Value("${otp.length}") int otpLength, @Value("${otp.expiration-minutes}") Duration otpExpirationMinutes,
                      @Value("${otp.sender}") String sender){
        this.otpRepository = otpRepository;
        this.emailService = emailService;
        this.otpLength = otpLength;
        this.otpExpirationMinutes = otpExpirationMinutes;
        this.sender = sender;
    }

    @Transactional
    public void generateAndSendOtp(String email){
        String otp = generateOtp();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(otpExpirationMinutes.toMinutes());

        OtpEntity otpEntity = new OtpEntity(email,otp,now,expiresAt);
        otpRepository.save(otpEntity);
        String body = "This your OTP for validation.\nOTP CODE : "+otp;
        String subject = "OTP VALIDATION";
        emailService.sendOtpEmail(sender,email,body,subject);
    }

    @Transactional
    public boolean validateOtp(String email,String otp){
        LocalDateTime now = LocalDateTime.now();
        Optional<OtpEntity> optionalOtp = otpRepository.findByEmailAndOtpAndUsedFalseAndExpiresAtAfter(email,otp,now);
        if(optionalOtp.isPresent()){
            OtpEntity otpEntity = optionalOtp.get();
            otpEntity.setUsed(true);
            otpRepository.save(otpEntity);
            return true;
        }
        return false;
    }

    @Transactional
    public void cleanupExpiredOtp(){
        otpRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }

    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for(int i =0 ;i<otpLength;i++){
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    @Transactional
    public void resendOtp(String email) {
        invalidateExistingOtps(email);

        String otp = generateOtp();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(otpExpirationMinutes.toMinutes());
        OtpEntity otpEntity = new OtpEntity(email,otp,now,expiresAt);
        otpRepository.save(otpEntity);
        String body = "This your OTP for validation.\nOTP CODE : "+otp;
        String subject = "OTP VALIDATION";
        emailService.sendOtpEmail(sender,email,body,subject);
    }

    private void invalidateExistingOtps(String email) {
        otpRepository.findByEmailAndUsedFalse(email)
                .forEach(otpEntity -> {
                    otpEntity.setUsed(true);
                    otpRepository.save(otpEntity);
                });
    }
}
