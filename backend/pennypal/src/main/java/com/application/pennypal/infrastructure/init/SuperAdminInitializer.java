package com.application.pennypal.infrastructure.init;

import com.application.pennypal.application.port.out.service.EncodePasswordPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.user.valueObject.Roles;
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
            User admin = User.create(
                    "Super Admin",
                    email,
                    encodePasswordPort.encode(password),
                    "9999999999",
                    Set.of(Roles.SUPER_ADMIN,Roles.ADMIN),
                    null
            );
            admin.verify();
            userRepositoryPort.save(admin);
        }
    }

}
