package com.application.pennypal.infrastructure.security.details;

import com.application.pennypal.domain.entity.User;
import com.application.pennypal.domain.valueObject.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user){
        this.user = user;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(Roles::toString)
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }


    @Override
    public boolean isAccountNonLocked(){
        return user.isActive();
    }

    @Override
    public boolean isEnabled(){
        return user.isVerified();
    }

    public User getUser(){
        return user;
    }
}
