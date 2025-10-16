package com.application.pennypal.infrastructure.adapter.out.user;

import com.application.pennypal.application.dto.output.user.UserUpdateApplicationOutput;
import com.application.pennypal.application.port.out.service.FileUploadPort;
import com.application.pennypal.application.port.out.service.TokenServicePort;
import com.application.pennypal.application.port.out.service.UpdateUserPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.service.auth.ValidateEmailUniqueness;
import com.application.pennypal.application.port.in.user.ReSendOtp;
import com.application.pennypal.domain.user.entity.User;

import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserAdapter implements UpdateUserPort {

    private final TokenServicePort tokenServicePort;
    private final UserRepositoryPort userRepositoryPort;
    private final FileUploadPort systemPort;
    private final ValidateEmailUniqueness validateEmailUniqueness;
    private final ReSendOtp reSendOtp;

    @Override
    public User update(UserUpdateApplicationOutput user, String token) {
        if(token != null){
            String currentEmail = tokenServicePort.getUsernameFromToken(token);
            if(currentEmail != null && !currentEmail.isEmpty()){
                User updatedUser = userRepositoryPort.findByEmail(currentEmail)
                        .orElseThrow(() -> new InfrastructureException("User not found with email given.","NOT_FOUND"));
                if(user.email() != null && !user.email().isBlank() && !user.email().equals(currentEmail)){
                    validateEmailUniqueness.validate(user.email());
                    updatedUser = updatedUser.changeEmail(user.email());
                }
                if(user.file() != null && !user.file().isEmpty()){
                    String profileURL = systemPort.uploadFile(user.file());
                    updatedUser = updatedUser.changeProfileURL(profileURL);
                }
                if(user.name() != null && !user.name().isEmpty()){
                    updatedUser = updatedUser.changeName(user.name());
                }
                if(user.phone() != null && !user.phone().isEmpty()){
                    updatedUser = updatedUser.changePhone(user.phone());
                }
                return userRepositoryPort.update(updatedUser);
            }
            else{
                throw new InfrastructureException("Email is null","NULL_EXCEPTION");
            }
        }
        else {
            throw  new InfrastructureException("Token is empty","MISSING_TOKEN");
        }

    }
}
