package com.application.pennypal.application.port.in.admin;

import com.application.pennypal.application.dto.input.user.RegisterInputModel;
import com.application.pennypal.application.dto.output.user.RegisterOutputModel;
import com.application.pennypal.application.dto.output.user.UserOutputModel;

public interface CreateAdmin {
    RegisterOutputModel execute(RegisterInputModel registerInputModel, String userId);
}
