package com.application.pennypal.application.service.user;

import com.application.pennypal.application.dto.input.user.RegisterInputModel;
import com.application.pennypal.application.dto.output.user.RegisterOutputModel;
import com.application.pennypal.application.mappers.user.UserApplicationMapper;
import com.application.pennypal.application.dto.output.user.UserOutputModel;
import com.application.pennypal.application.port.out.config.AuthConfigurationPort;
import com.application.pennypal.application.port.out.service.EncodePasswordPort;
import com.application.pennypal.application.port.out.service.FileUploadPort;
import com.application.pennypal.application.port.out.event.UserDomainEventPublisher;
import com.application.pennypal.application.service.auth.ValidateEmailUniqueness;
import com.application.pennypal.application.port.in.user.CreateUser;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.domain.user.event.UserRegisteredEvent;
import com.application.pennypal.domain.user.validator.PasswordValidator;
import com.application.pennypal.domain.user.valueObject.Roles;
import jakarta.transaction.Transactional;
import com.application.pennypal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@RequiredArgsConstructor
public class CreateUserService implements CreateUser {
    private final UserRepositoryPort userRepository;
    private final EncodePasswordPort encodePasswordPort;
    private final ValidateEmailUniqueness validateEmailUniqueness;
    private final FileUploadPort systemPort;
    private final UserDomainEventPublisher userDomainEventPublisher;
    private final AuthConfigurationPort authConfigurationPort;
    @Override
    @Transactional
    public RegisterOutputModel execute(RegisterInputModel registerInputModel) {
        validateEmailUniqueness.validate(registerInputModel.email());

        /// Set role as USER
        Set<Roles> roles = Set.of(Roles.USER);

        /// Validate password
        PasswordValidator.validate(registerInputModel.password());

        String encodedPassword = encodePasswordPort.encode(registerInputModel.password());
        String profileURL = systemPort.uploadFile(registerInputModel.userProfile());
        User user= User.create(registerInputModel.userName(), registerInputModel.email(),encodedPassword,registerInputModel.phone(),roles,profileURL);
        User registeredUser =  userRepository.save(user);

        /// Calculate TTL(Time to Live)
        Duration duration = authConfigurationPort.getOtpExpiration();
        LocalDateTime otpExpiresAt = LocalDateTime.now().plus(duration);

        /// Publish domain event for otp sending
        userDomainEventPublisher.publish(new UserRegisteredEvent(
                registeredUser.getUserId(),
                registerInputModel.email(),
                registerInputModel.userName()
        ));


        return new RegisterOutputModel(
                registeredUser.getUserId(),
                registerInputModel.email(),
                otpExpiresAt
        );
    }
}
