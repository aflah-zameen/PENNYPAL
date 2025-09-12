package com.application.pennypal.infrastructure.adapter.out.auth;

import com.application.pennypal.application.exception.usecase.user.InvalidCredentialsApplicationException;
import com.application.pennypal.application.exception.usecase.user.UserInactiveApplicationException;
import com.application.pennypal.application.exception.usecase.user.UserUnverifiedApplicationException;
import com.application.pennypal.application.port.out.service.UserAuthenticationPort;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringUserAuthenticationAdapter implements UserAuthenticationPort {

    private final AuthenticationManager authenticationManager;

    public SpringUserAuthenticationAdapter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void authenticate(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            log.info("User [{}] successfully authenticated", email);

        } catch (BadCredentialsException ex) {
            log.warn("Authentication failed for user [{}]: bad credentials", email);
            throw new InvalidCredentialsApplicationException("Username or password is incorrect");

        } catch (DisabledException ex) {
            log.warn("Authentication failed for user [{}]: user is blocked", email);
            throw new UserInactiveApplicationException("User is blocked");

        } catch (LockedException ex) {
            log.warn("Authentication failed for user [{}]: user is not verified", email);
            throw new UserUnverifiedApplicationException("User is not verified");

        } catch (UsernameNotFoundException ex) {
            log.warn("Authentication failed: user [{}] not found", email);
            throw new InvalidCredentialsApplicationException("User not found");

        } catch (Exception ex) {
            log.error("Unexpected error during authentication for user [{}]: {}", email, ex.getMessage(), ex);
            throw new InfrastructureException("Error during validating user", "UNKNOWN", ex);
        }
    }
}