package com.application.pennypal.infrastructure.init;

import com.application.pennypal.application.port.EncodePasswordPort;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.domain.entity.User;
import com.application.pennypal.domain.valueObject.Roles;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SuperAdminInitializer {
    private final UserRepositoryPort userRepositoryPort;
    private final EncodePasswordPort encodePasswordPort;

    @Value("${super.admin.email}")
    private String email;
    @Value("${super.admin.password}")
    private String password;

    @PostConstruct
    public void init(){
        if(!userRepositoryPort.existsByEmail(email)){
            User admin = new User();
            admin.setName("Super Admin");
            admin.setEmail(email);
            admin.setPassword(encodePasswordPort.encode(password));
            admin.setActive(true);
            admin.setVerified(true);
            admin.setRoles(Set.of(Roles.SUPER_ADMIN,Roles.ADMIN));
            admin.setPhone("8967452321");
            admin.setCreatedAt(Instant.now());
            userRepositoryPort.save(admin);
        }
    }

}
