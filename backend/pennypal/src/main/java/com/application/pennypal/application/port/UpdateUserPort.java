package com.application.pennypal.application.port;

import com.application.pennypal.application.output.user.UserUpdateApplicationOutput;
import com.application.pennypal.domain.entity.User;

public interface UpdateUserPort {
    User update(UserUpdateApplicationOutput user, String token);
}
