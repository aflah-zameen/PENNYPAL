package com.application.pennypal.application.port;

import com.application.pennypal.shared.exception.ApplicationException;

public interface UserAuthenticationPort {
    void authenticate(String email,String password) throws ApplicationException;
}
