package com.application.pennypal.infrastructure.security.details;

import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepositoryPort userRepositoryPort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositoryPort.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return new CustomUserDetails(user);
    }
}
