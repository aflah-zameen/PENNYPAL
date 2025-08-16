package com.application.pennypal.application.service.admin;

import com.application.pennypal.application.dto.input.user.RegisterInputModel;
import com.application.pennypal.application.dto.output.user.RegisterOutputModel;
import com.application.pennypal.application.dto.output.user.UserOutputModel;
import com.application.pennypal.application.mappers.user.UserApplicationMapper;
import com.application.pennypal.application.port.in.admin.CreateAdmin;
import com.application.pennypal.application.port.out.config.AuthConfigurationPort;
import com.application.pennypal.application.port.out.event.AdminDomainEventPublisher;
import com.application.pennypal.application.port.out.service.EncodePasswordPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.service.auth.ValidateEmailUniqueness;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.user.event.AdminRegisteredEvent;
import com.application.pennypal.domain.user.validator.PasswordValidator;
import com.application.pennypal.domain.user.valueObject.Roles;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class CreateAdminService implements CreateAdmin {

    private final ValidateEmailUniqueness validateEmailUniqueness;
    private final EncodePasswordPort encodePasswordPort;
    private final UserRepositoryPort userRepositoryPort;
    private final AdminDomainEventPublisher adminDomainEventPublisher;
    private final AuthConfigurationPort authConfigurationPort;

    @Override
    @Transactional
    public RegisterOutputModel execute(RegisterInputModel registerInputModel, String userId) {
        validateEmailUniqueness.validate(registerInputModel.email());

        /// Set role as admin
        Set<Roles> role = Set.of(Roles.ADMIN);

        /// Validate Password
        PasswordValidator.validate(registerInputModel.password());

        String encodedPassword = encodePasswordPort.encode(registerInputModel.password());
        User user = User.create(registerInputModel.userName(),registerInputModel.email(),
                encodedPassword,registerInputModel.phone(),role,null);
        User registeredUser = userRepositoryPort.save(user);

        /// Calculate TTL
        Duration duration = authConfigurationPort.getVerificationEmailExpiration();
        LocalDateTime expiresAt = LocalDateTime.now().plus(duration);

        /// Publish domain event
        adminDomainEventPublisher.publish(new AdminRegisteredEvent(
                registeredUser.getUserId(),
                registerInputModel.email(),
                registerInputModel.userName()
        ));

        return new RegisterOutputModel(
                registeredUser.getUserId(),
                registerInputModel.email(),
                expiresAt
        );
    }
}
