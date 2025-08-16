package com.application.pennypal.infrastructure.adapter.out.auth;

import com.application.pennypal.application.exception.usecase.user.InvalidCredentialsApplicationException;
import com.application.pennypal.application.exception.usecase.user.UserInactiveApplicationException;
import com.application.pennypal.application.exception.usecase.user.UserUnverifiedApplicationException;
import com.application.pennypal.application.port.out.service.UserAuthenticationPort;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SpringUserAuthenticationAdapter implements UserAuthenticationPort {
    private final AuthenticationManager authenticationManager;

    public SpringUserAuthenticationAdapter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void authenticate(String email, String password)  {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        }
        catch (BadCredentialsException ex){
            throw new InvalidCredentialsApplicationException("Username or password is incorrect");
        }
        catch (DisabledException ex){
            throw new UserInactiveApplicationException("User is blocked");
        }
        catch (LockedException ex){
            throw new UserUnverifiedApplicationException("User is not verified");
        }
        catch (Exception ex){
            throw new InfrastructureException("Error during validating user","UNKNOWN",ex);
        }
    }
}
