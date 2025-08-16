package com.application.pennypal.application.port.out.service;


public interface UserAuthenticationPort {
    void authenticate(String email,String password) ;
}
