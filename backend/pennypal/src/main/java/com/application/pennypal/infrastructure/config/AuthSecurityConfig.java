package com.application.pennypal.infrastructure.config;

import com.application.pennypal.domain.entity.User;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.infrastructure.security.details.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthSecurityConfig {
    private final UserRepositoryPort userRepository;
    AuthSecurityConfig(UserRepositoryPort userRepository){
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            User user = userRepository.findByEmail(username).orElse(null);
            if(user == null)
                throw new UsernameNotFoundException("User not found : "+username);
            return new CustomUserDetails(user);
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider() {
            @Override
            protected void additionalAuthenticationChecks(
                    org.springframework.security.core.userdetails.UserDetails userDetails,
                    org.springframework.security.authentication.UsernamePasswordAuthenticationToken authentication
            ) throws org.springframework.security.core.AuthenticationException {
                super.additionalAuthenticationChecks(userDetails, authentication);
                CustomUserDetails customUser = (CustomUserDetails) userDetails;
                if (!customUser.getUser().isActive()) {
                    throw new org.springframework.security.authentication.LockedException("User account is not active");
                }
                if (!customUser.getUser().isVerified()) {
                    throw new org.springframework.security.authentication.DisabledException("User account is not verified");
                }
            }
        };
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception{
        return authConfiguration.getAuthenticationManager();
    }


}
