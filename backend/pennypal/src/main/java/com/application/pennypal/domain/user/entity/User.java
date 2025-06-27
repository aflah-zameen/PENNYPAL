package com.application.pennypal.domain.user.entity;

import com.application.pennypal.shared.exception.DuplicateEmailException;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.domain.user.validator.*;
import com.application.pennypal.domain.user.valueObject.Roles;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString(exclude = "password")
@NoArgsConstructor
@Setter
public class User{
     private Long id;
     private String name;
     private String email;
     private String password;
     private String phone;
     private Set<Roles> roles;
     private boolean verified;
     private boolean active;
     private Instant createdAt;
     private Instant updatedAt;
     private String profileURL;

    public User(Long id, String name, String email, String password, String phone, Set<Roles> roles, boolean active,boolean verified,Instant createdAt,Instant updatedAt,String profileURL) {
        validate(name,email,password,phone,roles);
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.roles = Set.copyOf(roles);
        this.active = active;
        this.verified = verified;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.profileURL = profileURL;
    }

    public User(String name, String email, String encodedPassword, String phone, Set<Roles> roles,String profileURL) {
        validate(name, email, encodedPassword, phone, roles);
        this.id = null;
        this.name = name;
        this.email = email;
        this.password = encodedPassword;
        this.phone = phone;
        this.roles = Set.copyOf(roles);
        this.active = false;
        this.verified = false;
        this.createdAt = Instant.now();
        this.updatedAt = null;
        this.profileURL = profileURL;
    }

    public User activate(){
        if(active)
            throw new IllegalStateException("User is already active");
        if(!verified)
            throw new IllegalStateException("User must be verified to activate");
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public User deactivate(){
        if(!active)
            throw new IllegalStateException("User is already inactive");
        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public User verify(){
        if(verified)
            throw new IllegalStateException("User is already verified");
        this.verified = true;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public User changePassword(String newPassword, PasswordEncoder passwordEncoder) throws IllegalArgumentException{
        EncodedPasswordValidator.validate(newPassword);
        this.password = newPassword;
        this.updatedAt = Instant.now();
        return this;
    }

    public User removeRole(Roles role) throws IllegalStateException,IllegalArgumentException{
        if(roles.size()<=1)
            throw new IllegalStateException("User must have at least one role");
        Set<Roles> updatedRoles = new HashSet<>(roles);
        if(!updatedRoles.remove(role))
            throw  new IllegalArgumentException("Role not found : "+role.toString());
        this.roles = updatedRoles;
        return this;
    }
    public User addRole(Roles role) throws IllegalStateException{
        Set<Roles> updatedRoles = new HashSet<>(roles);
        updatedRoles.add(role);
        this.roles = updatedRoles;
        return this;
    }


    private void validate(String name, String email, String password, String phone, Set<Roles> roles) throws IllegalArgumentException{
        NameValidator.validate(name);
        EncodedPasswordValidator.validate(password);
        EmailValidator.validate(email);
        RoleValidator.validate(roles);
        if (phone == null || !phone.matches("\\d{10}")) throw new IllegalArgumentException("Invalid phone");
    }



 }