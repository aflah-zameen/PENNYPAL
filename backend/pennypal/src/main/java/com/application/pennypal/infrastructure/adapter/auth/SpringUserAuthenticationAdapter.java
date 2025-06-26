package com.application.pennypal.infrastructure.adapter.auth;

import com.application.pennypal.application.port.UserAuthenticationPort;
import com.application.pennypal.shared.exception.ApplicationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SpringUserAuthenticationAdapter implements UserAuthenticationPort {
    private final AuthenticationManager authenticationManager;

    public SpringUserAuthenticationAdapter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void authenticate(String email, String password) throws ApplicationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
    }
}
