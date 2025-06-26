package com.application.pennypal.interfaces.rest.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private String name;
    private String email;
    private String phone;
    private Set<String> roles;
    private boolean active;
    private boolean verified;
}
