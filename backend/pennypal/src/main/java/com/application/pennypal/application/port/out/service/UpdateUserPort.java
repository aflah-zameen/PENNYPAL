package com.application.pennypal.application.port.out.service;

import com.application.pennypal.application.dto.output.user.UserUpdateApplicationOutput;
import com.application.pennypal.domain.user.entity.User;

public interface UpdateUserPort {
    User update(UserUpdateApplicationOutput user, String token);
}
