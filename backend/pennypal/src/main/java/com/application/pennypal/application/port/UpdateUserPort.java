package com.application.pennypal.application.port;

import com.application.pennypal.application.dto.UserUpdateApplicationDTO;
import com.application.pennypal.domain.user.entity.User;

public interface UpdateUserPort {
    User update(UserUpdateApplicationDTO user,String token);
}
