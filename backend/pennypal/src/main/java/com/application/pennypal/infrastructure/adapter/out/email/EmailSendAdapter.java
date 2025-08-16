package com.application.pennypal.infrastructure.adapter.out.email;

import com.application.pennypal.application.port.out.service.EmailSendPort;
import com.application.pennypal.domain.user.validator.EmailValidator;
import com.application.pennypal.infrastructure.config.properties.AuthProperties;
import com.application.pennypal.infrastructure.exception.email.EmailSendFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSendAdapter implements EmailSendPort {
    private final JavaMailSender javaMailSender;
    private final AuthProperties authProperties;
    private final String EMAIL_VERIFICATION_URL;
    private final String RESET_PASSWORD_URL;
    EmailSendAdapter(
            JavaMailSender javaMailSender,
            AuthProperties authProperties,
            @Value("${frontend.baseurl.email-verification}") String emailBaseURL,
            @Value("${frontend.baseurl.reset-password}") String resetPasswordURL){
        this.javaMailSender = javaMailSender;
        this.authProperties = authProperties;
        this.EMAIL_VERIFICATION_URL = emailBaseURL;
        this.RESET_PASSWORD_URL = resetPasswordURL;

    }

    @Override
    public void sendUserVerificationOtp(String email,String userName,String otpCode) {
        EmailValidator.validate(email);
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");

            helper.setTo(email);
            helper.setSubject("Verify Your PennyPal Account");
            String htmlContent = buildHtmlOtpEmail(userName,otpCode,authProperties.verificationEmailExpiration().toMinutesPart());
            helper.setText(htmlContent,true);
            javaMailSender.send(message);
        }catch (MessagingException ex){
            throw new EmailSendFailedException("Failed to send email");
        }
    }

    @Override
    public void sendAdminVerification(String name,String email,String verificationToken) {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");

            helper.setTo(email);
            helper.setSubject("Verify Your PennyPal Admin Account");
            String htmlContent = buildHtmlAdminVerificationEmail(name,verificationToken,authProperties.verificationEmailExpiration().toMinutesPart(),EMAIL_VERIFICATION_URL);
            helper.setText(htmlContent,true);
            javaMailSender.send(message);
        }catch (MessagingException ex){
            throw new EmailSendFailedException("Failed to send email");
        }
    }

    @Override
    public void sendUserResetPass(String name, String email, String verificationToken) {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");

            helper.setTo(email);
            helper.setSubject("Reset Password");
            String htmlContent = buildHtmlResetPasswordEmail(name,verificationToken,authProperties.verificationEmailExpiration().toMinutesPart(),RESET_PASSWORD_URL);
            helper.setText(htmlContent,true);
            javaMailSender.send(message);
        }catch (MessagingException ex){
            throw new EmailSendFailedException("Failed to send email");
        }
    }

    private String buildHtmlOtpEmail(String userName, String otpCode, int expiryMinutes) {
        return """
        <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; background-color: #f5f5f5; border-radius: 10px;">
            <h2 style="color: #2c3e50;">👋 Hello, %s!</h2>
            <p style="font-size: 16px; color: #333;">
                Thank you for registering with <strong>PennyPal</strong>. Please use the OTP below to verify your email address:
            </p>
            <div style="text-align: center; margin: 30px 0;">
                <span style="display: inline-block; padding: 15px 30px; font-size: 26px; font-weight: bold; color: white; background-color: #6a1b9a; border-radius: 8px;">
                    %s
                </span>
            </div>
            <p style="font-size: 16px; color: #e74c3c; font-weight: bold; text-align: center;">
                ⚠️ This OTP is valid for %d minutes only.
            </p>
            <p style="font-size: 14px; color: #777;">
                Please do not share this OTP with anyone for your account security.
            </p>
            <p style="font-size: 14px; color: #aaa;">Regards,<br/>Team PennyPal</p>
        </div>
    """.formatted(userName, otpCode, expiryMinutes);
    }

    private String buildHtmlAdminVerificationEmail(String adminName, String verificationToken, int expiryMinutes, String frontendBaseUrl) {
        String verificationLink = frontendBaseUrl+verificationToken;

        return """
    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; background-color: #f5f5f5; border-radius: 10px;">
        <h2 style="color: #2c3e50;">👋 Hello, %s!</h2>
        <p style="font-size: 16px; color: #333;">
            Welcome to <strong>PennyPal</strong>. Your admin account has been created. To activate your account and set your password, please verify your email address by clicking the button below:
        </p>
        <div style="text-align: center; margin: 30px 0;">
            <a href="%s" style="display: inline-block; padding: 15px 30px; font-size: 18px; font-weight: bold; color: white; background-color: #6a1b9a; border-radius: 8px; text-decoration: none;">
                ✅ Verify Email
            </a>
        </div>
        <p style="font-size: 16px; color: #e67e22; font-weight: bold; text-align: center;">
            ⏳ This link is valid for %d minutes only.
        </p>
        <p style="font-size: 14px; color: #777;">
            If you didn’t request this email, you can safely ignore it.
        </p>
        <p style="font-size: 14px; color: #aaa;">Regards,<br/>Team PennyPal</p>
    </div>
    """.formatted(adminName, verificationLink, expiryMinutes);
    }

    private String buildHtmlResetPasswordEmail(String userName, String resetToken, int expiryMinutes, String frontendBaseUrl) {
        String resetLink = frontendBaseUrl + resetToken;

        return """
    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; background-color: #f0f8ff; border-radius: 10px;">
        <h2 style="color: #34495e;">🔐 Reset Your Password, %s</h2>
        <p style="font-size: 16px; color: #2c3e50;">
            We received a request to reset your password for your <strong>PennyPal</strong> account. If you initiated this, click the button below to set a new password:
        </p>
        <div style="text-align: center; margin: 30px 0;">
            <a href="%s" style="display: inline-block; padding: 15px 30px; font-size: 18px; font-weight: bold; color: white; background-color: #2980b9; border-radius: 8px; text-decoration: none;">
                🔄 Reset Password
            </a>
        </div>
        <p style="font-size: 16px; color: #e74c3c; font-weight: bold; text-align: center;">
            ⏳ This link will expire in %d minutes for your security.
        </p>
        <p style="font-size: 14px; color: #777;">
            If you didn’t request this, no worries — your account is still safe. Just ignore this message.
        </p>
        <p style="font-size: 14px; color: #aaa;">Warm regards,<br/>Team PennyPal</p>
    </div>
    """.formatted(userName, resetLink, expiryMinutes);
    }



}
