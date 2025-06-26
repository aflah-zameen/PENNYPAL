package com.application.pennypal.application.usecases.user;

import com.application.pennypal.domain.user.entity.User;

public interface CreateUser {
    User execute (String name,String email,String password,String phone,String role);
}
