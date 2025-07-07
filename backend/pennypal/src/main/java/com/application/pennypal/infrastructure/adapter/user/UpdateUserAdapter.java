package com.application.pennypal.infrastructure.adapter.user;

import com.application.pennypal.application.dto.UserUpdateApplicationDTO;
import com.application.pennypal.application.port.S3SystemPort;
import com.application.pennypal.application.port.TokenServicePort;
import com.application.pennypal.application.port.UpdateUserPort;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.application.service.auth.ValidateEmailUniqueness;
import com.application.pennypal.application.usecases.user.SendOtp;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.shared.exception.ApplicationException;
import com.application.pennypal.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserAdapter implements UpdateUserPort {

    private final TokenServicePort tokenServicePort;
    private final UserRepositoryPort userRepositoryPort;
    private final S3SystemPort systemPort;
    private final ValidateEmailUniqueness validateEmailUniqueness;
    private final SendOtp sendOtp;

    @Override
    public User update(UserUpdateApplicationDTO user,String token) {
        if(token != null){
            String currentEmail = tokenServicePort.getUsernameFromToken(token);
            if(currentEmail != null && !currentEmail.isEmpty()){
                User updatedUser = userRepositoryPort.findByEmail(currentEmail)
                        .orElseThrow(() -> new UserNotFoundException("User not found with email given."));
                if(user.email() != null && !user.email().isBlank() && !user.email().equals(currentEmail)){
                    validateEmailUniqueness.validate(user.email());
                    updatedUser.setEmail(user.email());
                }
                if(user.file() != null && !user.file().isEmpty()){
                    String profileURL = systemPort.uploadFile(user.file());
                    updatedUser.setProfileURL(profileURL);
                }
                if(user.name() != null && !user.name().isEmpty()){
                    updatedUser.setName(user.name());
                }
                if(user.phone() != null && !user.phone().isEmpty()){
                    updatedUser.setPhone(user.phone());
                }
                return userRepositoryPort.save(updatedUser);
            }
            else{
                throw new ApplicationException("Email is null","400");
            }
        }
        else {
            throw  new ApplicationException("Token is empty","403");
        }

    }
}
